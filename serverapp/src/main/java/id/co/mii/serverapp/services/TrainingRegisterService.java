package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.requests.TrainingRegisterRequest;
import id.co.mii.serverapp.repositories.TrainingRegisterRepository;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class TrainingRegisterService extends BaseService<TrainingRegister, Integer> {
  private TrainingService trainingService;
  private EmployeeService employeeService;
  private TrainingRegisterRepository trainingRegisterRepository;
  private RoleService roleService;
  private StatusService statusService;
  private HistoryService historyService;

  @SneakyThrows
  public TrainingRegister createCancellation(TrainingRegisterRequest trainingRegisterRequest) {
    Status success = statusService.getById(1);
    Status requestCancel = statusService.getById(5);
    Training training = trainingService.getById(trainingRegisterRequest.getTrainingId());
    Employee trainee = employeeService.getById(trainingRegisterRequest.getTraineeId());
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    if (!loggedInEmp.equals(trainee)) {
      Role admin = roleService.getById(1);
      if (!loggedInEmp.getUser().getRoles().contains(admin)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot request cancel other trainee");
      }
    }
    long count = getAll()
            .stream()
            .filter(tr -> tr.getTraining().equals(training) && tr.getTrainee().equals(trainee) && tr.getCurrentStatus().equals(success))
            .count();
    if (count == 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Trainee not registered for this training");
    }
    TrainingRegister trainingRegister = new TrainingRegister();
    trainingRegister.setTraining(training);
    trainingRegister.setTrainee(trainee);
    trainingRegister.setCurrentStatus(requestCancel);
    trainingRegister.setCreatedBy(loggedInEmp.getUser().getUsername());
    trainingRegister.setUpdatedBy(loggedInEmp.getUser().getUsername());
    trainingRegister.setAttachment(trainingRegisterRequest.getAttachment().getBytes());

    TrainingRegister savedTrainingRegister = create(trainingRegister);

    History history = new History();
    history.setTrainingRegister(savedTrainingRegister);
    history.setStatus(requestCancel);
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
    long registeredTrainee = getAll()
            .stream()
            .filter(tr -> tr.getTraining().equals(training) && tr.getCurrentStatus().equals(success))
            .count();
    if (training.getQuota() == (int) registeredTrainee) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration has exceeded the quota");
    }
    if (trainingRegisterRequest.getStatusId() != null) {
      Status currentStatus = statusService.getById(trainingRegisterRequest.getStatusId());
      trainingRegister.setCurrentStatus(currentStatus);
      historyService.create(new History(trainingRegister, currentStatus, trainingRegisterRequest.getNotes()));
    }
    trainingRegister.setUpdatedAt(LocalDateTime.now());
    trainingRegister.setUpdatedBy(employeeService.getLoggedInEmployee().getUser().getUsername());
    return trainingRegisterRepository.save(trainingRegister);
  }
}
