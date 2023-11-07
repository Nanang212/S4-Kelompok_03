package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.repositories.TrainingRepository;
import id.co.mii.serverapp.services.base.BaseService;
import id.co.mii.serverapp.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainingService extends BaseService<Training, Integer> {
  private ModelMapper modelMapper;
  private EmployeeService employeeService;
  private TrainingRepository trainingRepository;
  private RoleService roleService;
  private StatusService statusService;

//  public Training registerTraineeToTraining(Integer trainingId, Integer traineeId) {
//    Training training = getById(trainingId);
//    Employee trainee = employeeService.getById(traineeId);
//    List<Employee> trainees = training.getTrainees();
//    Employee loggedInEmp = employeeService.getLoggedInEmployee();
//
//    if (!loggedInEmp.equals(trainee)) {
//      Role admin = roleService.getById(1);
//      if (!loggedInEmp.getUser().getRoles().contains(admin)) {
//        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot register other trainee");
//      }
//    }
//    if (trainees.contains(trainee)) {
//      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee " + trainee.getName() + " is already participating");
//    }
//    if (!trainee.getAttendedTrainings().isEmpty()) {
//      trainee.getAttendedTrainings().forEach(tr -> {
//        Date traineeStartDate = tr.getStartDate();
//        Date traineeEndDate = tr.getEndDate();
//        Date trainingStartDate = training.getStartDate();
//        Date trainingEndDate = training.getEndDate();
//        if (trainingStartDate.equals(traineeStartDate)
//                || (trainingStartDate.after(traineeStartDate) && trainingStartDate.before(traineeEndDate))) {
//          throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee " + trainee.getName() + " is having other attended training");
//        }
//        if (trainingEndDate.equals(traineeEndDate)
//                || (trainingEndDate.after(traineeStartDate) && trainingEndDate.before(traineeEndDate))) {
//          throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee " + trainee.getName() + " is having other attended training");
//        }
//      });
//    }
//    trainees.add(trainee);
//    training.setTrainees(trainees);
//    return trainingRepository.save(training);
//  }

  public List<Training> getAllByTrainer(String username) {
    Employee employee = employeeService.findByUsername(username);
    Role trainerRole = roleService.getById(2);
    if (!employee.getUser().getRoles().contains(trainerRole)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, employee.getName() + " is not Trainer");
    }
    return employee.getTrainings();
  }

  public List<Training> getAllByTrainee(String username) {
    Employee trainee = employeeService.findByUsername(username);
    Role trainerRole = roleService.getById(3);
    if (!trainee.getUser().getRoles().contains(trainerRole)) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, trainee.getName() + " is not Trainee");
    }
    return trainee.getTrainingRegisters()
            .stream()
            .filter(trainingRegister -> trainingRegister.getCurrentStatus().equals(statusService.getById(1)))
            .map(TrainingRegister::getTraining)
            .collect(Collectors.toList());
  }

  public Training create(TrainingRequest trainingRequest) {
    Training training = modelMapper.map(trainingRequest, Training.class);
    Employee currentEmp = employeeService.getLoggedInEmployee();
    Employee trainer = null;
    if (trainingRequest.getTrainerId() != null) {
      trainer = employeeService.getById(trainingRequest.getTrainerId());
//      trainer.getTrainings()
//              .forEach(tr -> {
//                Date trainerStartDate = tr.getStartDate();
//                Date trainerEndDate = tr.getEndDate();
//                Date trainingStartDate = training.getStartDate();
//                Date trainingEndDate = training.getEndDate();
//                if (trainingStartDate.equals(trainerStartDate)
//                        || (trainingStartDate.after(trainerStartDate) && trainingStartDate.before(trainerEndDate))) {
//                  throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
//                }
//                if (trainingEndDate.equals(trainerEndDate)
//                        || (trainingEndDate.after(trainerStartDate) && trainingEndDate.before(trainerEndDate))) {
//                  throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
//                }
//              });
    }
    training.setTrainer(trainer);
    training.setCreatedBy(currentEmp.getUser().getUsername());
    training.setUpdatedBy(currentEmp.getUser().getUsername());
    return create(training);
  }

  public Training update(Integer id, TrainingRequest trainingRequest) {
    Training training = getById(id);
    if (!StringUtils.isEmptyOrNull(trainingRequest.getTitle())) {
      training.setTitle(trainingRequest.getTitle());
    }
    if (trainingRequest.getStartDate() != null) {
//      if (training.getTrainer() != null) {
//        training.getTrainer().getTrainings()
//                .forEach(tr -> {
//                  if (!tr.equals(training)) {
//                    Date trainerStartDate = tr.getStartDate();
//                    Date trainerEndDate = tr.getEndDate();
//                    Date trainingStartDate = training.getStartDate();
//                    Date trainingEndDate = training.getEndDate();
//                    if (trainingStartDate.equals(trainerStartDate)
//                            || (trainingStartDate.after(trainerStartDate) && trainingStartDate.before(trainerEndDate))) {
//                      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
//                    }
//                    if (trainingEndDate.equals(trainerEndDate)
//                            || (trainingEndDate.after(trainerStartDate) && trainingEndDate.before(trainerEndDate))) {
//                      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainer " + tr.getTrainer().getName() + " is having other mentoring schedule");
//                    }
//                  }
//                });
//      }
      training.setStartDate(trainingRequest.getStartDate());
    }
    if (trainingRequest.getEndDate() != null) {
      training.setEndDate(trainingRequest.getEndDate());
    }
    if (trainingRequest.getQuota() != null) {
      training.setQuota(training.getQuota());
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
