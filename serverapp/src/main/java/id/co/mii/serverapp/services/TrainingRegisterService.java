package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.TrainingRegister;
import id.co.mii.serverapp.models.dto.requests.TrainingRegisterRequest;
import id.co.mii.serverapp.repositories.TrainingRegisterRepository;
import id.co.mii.serverapp.services.base.BaseService;
import id.co.mii.serverapp.utils.StringUtils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@Service
@AllArgsConstructor
public class TrainingRegisterService extends BaseService<TrainingRegister, Integer> {
  private TrainingService trainingService;
  private EmployeeService employeeService;
  private TrainingRegisterRepository trainingRegisterRepository;
  private RoleService roleService;

  public TrainingRegister create(TrainingRegisterRequest trainingRegisterRequest) {
    Training training = trainingService.getById(trainingRegisterRequest.getTrainingId());
    Employee trainee = employeeService.getById(trainingRegisterRequest.getTraineeId());
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    if (!loggedInEmp.equals(trainee)) {
      Role admin = roleService.getById(1);
      if (!loggedInEmp.getUser().getRoles().contains(admin)) {
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot register other trainee");
      }
    }
    trainee.getTrainingRegisters().forEach(trainingRegister -> {
      if (trainingRegister.getStatus().equalsIgnoreCase("success")) {
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
    return trainingRegisterRepository.save(new TrainingRegister(training, trainee, "Pending", null));
  }

  public TrainingRegister update(Integer id, TrainingRegisterRequest trainingRegisterRequest) {
    TrainingRegister trainingRegister = getById(id);
    if (!StringUtils.isEmptyOrNull(trainingRegisterRequest.getStatus())) {
      trainingRegister.setStatus(trainingRegisterRequest.getStatus());
    }
    return trainingRegisterRepository.save(trainingRegister);
  }
}
