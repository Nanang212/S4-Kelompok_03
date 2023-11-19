package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.requests.EmailRequest;
import id.co.mii.serverapp.models.dto.requests.TrainingRegisterRequest;
import id.co.mii.serverapp.models.dto.responses.TrainingRegisterResponse;
import id.co.mii.serverapp.repositories.TrainingRegisterRepository;
import id.co.mii.serverapp.repositories.TrainingRepository;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingRegisterService extends BaseService<TrainingRegister, Integer> {
  private TrainingService trainingService;
  private EmployeeService employeeService;
  private TrainingRegisterRepository trainingRegisterRepository;
  private RoleService roleService;
  private StatusService statusService;
  private HistoryService historyService;
  private EmailService emailService;
  private TrainingRepository trainingRepository;

  public TrainingRegister getByTrainingAndLoggedInEmployee(Integer trainingId) {
    Status success = statusService.getById(1);
    Training training = trainingService.getById(trainingId);
    Employee trainee = employeeService.getLoggedInEmployee();
    return trainingRegisterRepository
            .findByCurrentStatusAndTrainingAndTrainee(success, training, trainee)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration is not found"));
  }

  public List<TrainingRegisterResponse> getAllGroupByTraining(){
    List<Training> trainings = trainingService.getAll();
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    Role trainer = roleService.getById(2);
    Role trainee = roleService.getById(3);
    Status success = statusService.getById(1);
    Status pending = statusService.getById(2);
    if (loggedInEmp.getUser().getRoles().contains(trainer)) {
      return trainings.stream()
              .filter(training -> !training.getTrainingRegisters().isEmpty())
              .filter(training -> training.getTrainer() != null && training.getTrainer().equals(loggedInEmp))
              .map(this::customMapFrom)
              .collect(Collectors.toList());
    }
    if (loggedInEmp.getUser().getRoles().contains(trainee)) {
      return loggedInEmp.getTrainingRegisters()
              .stream()
              .filter(trainingRegister -> trainingRegister.getCurrentStatus().equals(success) || trainingRegister.getCurrentStatus().equals(pending))
              .map(trainingRegister -> customMapFrom(trainingRegister.getTraining()))
              .collect(Collectors.toList());
    }
    return trainings.stream()
            .filter(training -> !training.getTrainingRegisters().isEmpty())
            .map(this::customMapFrom)
            .collect(Collectors.toList());
  }

  private TrainingRegisterResponse customMapFrom(Training training) {
    TrainingRegisterResponse trainingRegisterResponse = new TrainingRegisterResponse();
    List<Map<String, Object>> traineeStatus = training.getTrainingRegisters()
            .stream()
            .map(trainingRegister -> {
              Map<String, Object> map = new HashMap<>();
              map.put("trainingRegisterId", trainingRegister.getId());
              map.put("trainee", trainingRegister.getTrainee());
              map.put("status", trainingRegister.getCurrentStatus());
              return map;
            })
            .collect(Collectors.toList());
    trainingRegisterResponse.setTraining(training);
    trainingRegisterResponse.setTrainer(training.getTrainer());
    trainingRegisterResponse.setTraineeStatus(traineeStatus);
    return trainingRegisterResponse;
  }

  public List<TrainingRegisterResponse> getAllCancellationGroupByTraining(){
    List<Training> trainings = trainingService.getAll();
    Status requestCancel = statusService.getById(5);
    return trainings.stream()
            .filter(training -> !training.getTrainingRegisters().isEmpty())
            .map(training -> {
              TrainingRegisterResponse trainingRegisterResponse = new TrainingRegisterResponse();
              List<Map<String, Object>> traineeStatus = training.getTrainingRegisters()
                      .stream()
                      .filter(trainingRegister -> trainingRegister.getCurrentStatus().equals(requestCancel))
                      .map(trainingRegister -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("trainingRegisterId", trainingRegister.getId());
                        map.put("trainee", trainingRegister.getTrainee());
                        map.put("status", trainingRegister.getCurrentStatus());
                        return map;
                      })
                      .collect(Collectors.toList());
              trainingRegisterResponse.setTraining(training);
              trainingRegisterResponse.setTrainer(training.getTrainer());
              trainingRegisterResponse.setTraineeStatus(traineeStatus);
              return trainingRegisterResponse;
            })
            .filter(trainingRegisterResponse -> !trainingRegisterResponse.getTraineeStatus().isEmpty())
            .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getByIdGroupByTraining(Integer id) {
    Training training = trainingService.getById(id);
    Status requestCancel = statusService.getById(5);
    Status cancelled = statusService.getById(4);
    return training.getTrainingRegisters()
            .stream()
            .filter(trainingRegister -> !trainingRegister.getCurrentStatus().equals(requestCancel) && !trainingRegister.getCurrentStatus().equals(cancelled))
            .map(tr -> {
              Map<String, Object> map = new HashMap<>();
              map.put("id", tr.getId());
              map.put("trainee", tr.getTrainee());
              map.put("status", tr.getCurrentStatus());
              return map;
            })
            .collect(Collectors.toList());
  }

  public List<Map<String, Object>> getCancellationByIdGroupByTraining(Integer id) {
    Training training = trainingService.getById(id);
    Status cancelled = statusService.getById(4);
    Status requestCancel = statusService.getById(5);
    return training.getTrainingRegisters()
            .stream()
            .filter(trainingRegister -> trainingRegister.getCurrentStatus().equals(requestCancel) || trainingRegister.getCurrentStatus().equals(cancelled))
            .map(tr -> {
              Map<String, Object> map = new HashMap<>();
              map.put("id", tr.getId());
              map.put("trainee", tr.getTrainee());
              map.put("status", tr.getCurrentStatus());
              map.put("reason", tr.getNotes());
              return map;
            })
            .collect(Collectors.toList());
  }

  public List<TrainingRegister> getAll() {
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    Role admin = roleService.getById(1);
    if (!loggedInEmp.getUser().getRoles().contains(admin)) {
      return trainingRegisterRepository.findAll()
              .stream()
              .filter(trainingRegister -> trainingRegister.getTrainee().equals(loggedInEmp))
              .collect(Collectors.toList());
    }
    return super.getAll();
  }

  @SneakyThrows
  public TrainingRegister createCancellation(TrainingRegisterRequest trainingRegisterRequest) {
    Status success = statusService.getById(1);
    Status requestCancel = statusService.getById(5);
    Training training = trainingService.getById(trainingRegisterRequest.getTrainingId());
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    TrainingRegister trainingRegister = training.getTrainingRegisters()
            .stream()
            .filter(tr -> tr.getTrainee().equals(loggedInEmp))
            .filter(tr -> tr.getCurrentStatus().equals(success))
            .findAny()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registration is not found"));

    trainingRegister.setCurrentStatus(requestCancel);
    trainingRegister.setNotes(trainingRegisterRequest.getNotes());
    trainingRegister.setCreatedBy(loggedInEmp.getUser().getUsername());
    trainingRegister.setUpdatedBy(loggedInEmp.getUser().getUsername());

    TrainingRegister savedTrainingRegister = create(trainingRegister);

    training.setAvailSeat(training.getAvailSeat() + 1);
    trainingRepository.save(training);

    History history = new History();
    history.setTrainingRegister(savedTrainingRegister);
    history.setStatus(requestCancel);
    history.setNotes(trainingRegisterRequest.getNotes());
    historyService.create(history);

    return savedTrainingRegister;
  }

  public byte[] getAttachmentById(Integer id) {
    TrainingRegister trainingRegister = getById(id);
    return trainingRegister.getAttachment();
  }

  @SneakyThrows
  public TrainingRegister create(TrainingRegisterRequest trainingRegisterRequest) {
    Status pending = statusService.getById(2);
    Status success = statusService.getById(1);
    Training training = trainingService.getById(trainingRegisterRequest.getTrainingId());
    Employee trainee = employeeService.getById(trainingRegisterRequest.getTraineeId());
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    if (training.getAvailSeat() == 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Quota has reached the maximum value");
    }
    if (!loggedInEmp.equals(trainee)) {
      Role admin = roleService.getById(1);
      if (!loggedInEmp.getUser().getRoles().contains(admin)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot register other trainee");
      }
    }
    if (training.getQuota() != null) {
      long registeredTrainee = getAll()
              .stream()
              .filter(trainingRegister -> trainingRegister.getTraining().equals(training) && trainingRegister.getCurrentStatus().equals(success))
              .count();
      if (training.getQuota() == (int) registeredTrainee) {
        throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration has exceeded the quota");
      }
    }
    trainee.getTrainingRegisters().forEach(trainingRegister -> {
      if (trainingRegister.getCurrentStatus().equals(statusService.getById(1))) {
        Date traineeStartDate = trainingRegister.getTraining().getStartDate();
        Date traineeEndDate = trainingRegister.getTraining().getEndDate();
        Date trainingStartDate = training.getStartDate();
        Date trainingEndDate = training.getEndDate();
        if (trainingStartDate.equals(traineeStartDate)
                || (trainingStartDate.after(traineeStartDate) && trainingStartDate.before(traineeEndDate))) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee " + trainee.getName() + " is having another attended training");
        }
        if (trainingEndDate.equals(traineeEndDate)
                || (trainingEndDate.after(traineeStartDate) && trainingEndDate.before(traineeEndDate))) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee " + trainee.getName() + " is having another attended training");
        }
      }
    });
    TrainingRegister trainingRegister = new TrainingRegister();
    trainingRegister.setTraining(training);
    trainingRegister.setTrainee(trainee);
    trainingRegister.setAttachment(trainingRegisterRequest.getAttachment().getBytes());
    trainingRegister.setCurrentStatus(pending);
    trainingRegister.setCreatedBy(loggedInEmp.getUser().getUsername());
    trainingRegister.setUpdatedBy(loggedInEmp.getUser().getUsername());

    TrainingRegister savedTrainingRegister = create(trainingRegister);

    History history = new History();
    history.setTrainingRegister(savedTrainingRegister);
    history.setStatus(pending);
    historyService.create(history);
    return savedTrainingRegister;
  }

  public TrainingRegister update(Integer id, TrainingRegisterRequest trainingRegisterRequest) {
    Status success = statusService.getById(1);
    TrainingRegister trainingRegister = getById(id);
    Training training = trainingRegister.getTraining();
    if (trainingRegisterRequest.getStatusId() != null) {
      Status currentStatus = statusService.getById(trainingRegisterRequest.getStatusId());
      if (currentStatus.equals(success)) {
        training.setAvailSeat(training.getAvailSeat() - 1);
      }
      trainingRegister.setCurrentStatus(currentStatus);
      historyService.create(new History(trainingRegister, currentStatus, trainingRegisterRequest.getNotes()));
    }
    trainingRegister.setUpdatedAt(LocalDateTime.now());
    trainingRegister.setUpdatedBy(employeeService.getLoggedInEmployee().getUser().getUsername());

    EmailRequest emailRequest = new EmailRequest();
    Map<String, Object> properties = new HashMap<>();
    properties.put("id", training.getId());
    properties.put("title", training.getTitle());
    properties.put("startDate", training.getStartDate());
    properties.put("endDate", training.getEndDate());
    properties.put("status", trainingRegister.getCurrentStatus().getName());
    properties.put("notes", trainingRegisterRequest.getNotes());
    properties.put("name", trainingRegister.getTrainee().getName());
    properties.put("isOnline", trainingRegister.getTraining().getIsOnline());
    properties.put("platformUrl", trainingRegister.getTraining().getPlatformUrl());
    properties.put("address", trainingRegister.getTraining().getAddress());
    properties.put("trainingLink", "http://localhost:9090/training/" + training.getId());
    emailRequest.setTo(trainingRegister.getTrainee().getEmail());
    emailRequest.setSubject("Training Registration - " + training.getId());
    emailRequest.setBody("trainingRegister.html");
    emailRequest.setProperties(properties);
    emailService.sendHtmlMessage(emailRequest);

    return trainingRegisterRepository.save(trainingRegister);
  }
}
