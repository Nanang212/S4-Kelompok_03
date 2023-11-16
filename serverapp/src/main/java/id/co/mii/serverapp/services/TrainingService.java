package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.repositories.TrainingRegisterRepository;
import id.co.mii.serverapp.repositories.TrainingRepository;
import id.co.mii.serverapp.services.base.BaseService;
import id.co.mii.serverapp.utils.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class TrainingService extends BaseService<Training, Integer> {
  private ModelMapper modelMapper;
  private EmployeeService employeeService;
  private TrainingRepository trainingRepository;
  private RoleService roleService;
  private StatusService statusService;
  private EmailService emailService;
  private CategoryService categoryService;

  public List<Training> getAll(Integer categoryId) {
    if (categoryId != null) {
      return trainingRepository.findAllByCategoryId(categoryId)
              .stream()
              .sorted(Comparator.comparing(Training::getStartDate).reversed())
              .collect(Collectors.toList());
    }
    return super.getAll()
            .stream()
            .sorted(Comparator.comparing(Training::getStartDate).reversed())
            .collect(Collectors.toList());
  }

  public List<Integer> getTrainingByAllMonthInYear() {
    List<Integer> countTraining = new ArrayList<>();
    for (int i = 0; i < 12; i++) {
      countTraining.add(getTrainingByMonth(i + 1));
    }
    return countTraining;
  }

  public int getTrainingByMonth(int monthVal) {
    return (int) getAll()
            .stream()
            .filter(training -> training.getStartDate().toInstant().atZone(ZoneId.systemDefault()).getMonthValue() == monthVal)
            .count();
  }

  public Integer countTrainingInYear() {
    long count = getAll()
            .stream()
            .filter(training -> {
              int currentYear = LocalDate.now().getYear();
              return training.getStartDate().toInstant().atZone(ZoneId.systemDefault()).getYear() == currentYear;
            })
            .count();
    return (int) count;
  }

// TODO : validasi input start date & end date
  public List<Training> getAllByTrainer(String username) {
    Employee employee = employeeService.findByUsername(username);
    Role admin = roleService.getById(1);
    Role trainerRole = roleService.getById(2);
    if (!employee.getUser().getRoles().contains(trainerRole) && !employee.getUser().getRoles().contains(admin)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, employee.getName() + " is not Trainer");
    }
    return employee.getTrainings();
  }

  public List<Training> getAllByTrainee(String username) {
    Employee trainee = employeeService.findByUsername(username);
    return trainee.getTrainingRegisters()
            .stream()
            .filter(trainingRegister -> trainingRegister.getCurrentStatus().equals(statusService.getById(1)))
            .map(TrainingRegister::getTraining)
            .collect(Collectors.toList());
  }

  public Training broadcastEmail(Integer id) {
    Training training = getById(id);
    employeeService.getAllByRoleId(3).forEach(employee -> {
      EmailRequest emailRequest = new EmailRequest();
      Map<String, Object> properties = new HashMap<>();
      properties.put("id", training.getId());
      properties.put("title", training.getTitle());
      properties.put("startDate", training.getStartDate());
      properties.put("endDate", training.getEndDate());
      properties.put("quota", training.getQuota());
      properties.put("trainingLink", "http://localhost:9090/training/" + training.getId());
      emailRequest.setTo(employee.getEmail());
      emailRequest.setSubject("New Training Appear");
      emailRequest.setBody("training.html");
      emailRequest.setProperties(properties);
      emailService.sendHtmlMessage(emailRequest);
    });
    return training;
  }

  public Training create(TrainingRequest trainingRequest) {
    Category done = categoryService.getById(1);
    Category ongoing = categoryService.getById(2);
    Category upcoming = categoryService.getById(3);
    Training training = modelMapper.map(trainingRequest, Training.class);
    Employee currentEmp = employeeService.getLoggedInEmployee();
    Employee trainer = null;
    Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    if (trainingRequest.getStartDate().before(currentDate) || trainingRequest.getEndDate().before(currentDate)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to set start date or end date before current date");
    }
    if (trainingRequest.getStartDate().after(trainingRequest.getEndDate())) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trying to set start date before end date");
    }
    if (trainingRequest.getTrainerId() != null) {
      trainer = employeeService.getById(trainingRequest.getTrainerId());
      trainer.getTrainings()
              .forEach(tr -> {
                Date trainerStartDate = tr.getStartDate();
                Date trainerEndDate = tr.getEndDate();
                Date trainingStartDate = training.getStartDate();
                Date trainingEndDate = training.getEndDate();
                if (trainingStartDate.equals(trainerStartDate)
                        || (trainingStartDate.after(trainerStartDate) && trainingStartDate.before(trainerEndDate))) {
                  throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
                }
                if (trainingEndDate.equals(trainerEndDate)
                        || (trainingEndDate.after(trainerStartDate) && trainingEndDate.before(trainerEndDate))) {
                  throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
                }
              });
    }
    training.setTrainer(trainer);
    training.setAvailSeat(training.getQuota());
    training.setCreatedBy(currentEmp.getUser().getUsername());
    training.setUpdatedBy(currentEmp.getUser().getUsername());
    training.setCategory(upcoming);
    return create(training);
  }

  public Training update(Integer id, TrainingRequest trainingRequest) {
    Training training = getById(id);
    if (!StringUtils.isEmptyOrNull(trainingRequest.getTitle())) {
      training.setTitle(trainingRequest.getTitle());
    }
    if (trainingRequest.getStartDate() != null) {
      if (training.getTrainer() != null) {
        training.getTrainer().getTrainings()
                .forEach(tr -> {
                  if (!tr.equals(training)) {
                    Date trainerStartDate = tr.getStartDate();
                    Date trainerEndDate = tr.getEndDate();
                    Date trainingStartDate = training.getStartDate();
                    Date trainingEndDate = training.getEndDate();
                    if (trainingStartDate.equals(trainerStartDate)
                            || (trainingStartDate.after(trainerStartDate) && trainingStartDate.before(trainerEndDate))) {
                      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
                    }
                    if (trainingEndDate.equals(trainerEndDate)
                            || (trainingEndDate.after(trainerStartDate) && trainingEndDate.before(trainerEndDate))) {
                      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
                    }
                  }
                });
      }
      training.setStartDate(trainingRequest.getStartDate());
    }
    if (trainingRequest.getEndDate() != null) {
      training.setEndDate(trainingRequest.getEndDate());
    }
    if (trainingRequest.getQuota() != null) {
      training.setQuota(training.getQuota());
    }
    if (trainingRequest.getDuration() != null) {
      training.setDuration(trainingRequest.getDuration());
    }
    if (!StringUtils.isEmptyOrNull(trainingRequest.getAddress())) {
      training.setAddress(trainingRequest.getAddress());
    }
    if (!StringUtils.isEmptyOrNull(trainingRequest.getPlatformUrl())) {
      training.setPlatformUrl(trainingRequest.getPlatformUrl());
    }
    if (trainingRequest.getIsOnline() != null) {
      training.setIsOnline(trainingRequest.getIsOnline());
    }
    if (trainingRequest.getTrainerId() != null) {
      Employee trainer = employeeService.getById(trainingRequest.getTrainerId());
      training.setTrainer(trainer);
    }
    return trainingRepository.save(training);
  }
}
