package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TrainingService extends BaseService<Training, Integer> {
  private ModelMapper modelMapper;
  private EmployeeService employeeService;

  public Training create(TrainingRequest trainingRequest) {
    Training training = modelMapper.map(trainingRequest, Training.class);
    Employee currentEmp = employeeService.getLoggedInEmployee();
    Employee trainer = null;
    if (trainingRequest.getTrainerId() != null) {
      trainer = employeeService.getById(trainingRequest.getTrainerId());
    }
    // TODO : Validasi untuk date
    training.setTrainer(trainer);
    training.setCreatedBy(currentEmp.getUser().getUsername());
    training.setUpdatedBy(currentEmp.getUser().getUsername());
    return create(training);
  }
}
