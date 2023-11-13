package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.*;
import id.co.mii.serverapp.models.dto.responses.HistoryResponse;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HistoryService extends BaseService<History, Integer> {
  private EmployeeService employeeService;
  private RoleService roleService;
  private TrainingService trainingService;

  @Override
  public List<History> getAll() {
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    Role admin = roleService.getById(1);
    if (!loggedInEmp.getUser().getRoles().contains(admin)) {
      return super.getAll()
              .stream()
              .filter(history -> history.getTrainingRegister().getTrainee().equals(loggedInEmp))
              .collect(Collectors.toList());
    }
    return super.getAll();
  }

  public List<HistoryResponse> getHistoriesByTraining() {
    Employee loggedInEmp = employeeService.getLoggedInEmployee();
    return trainingService.getAll()
            .stream()
            .map(training -> {
              HistoryResponse historyResponse = new HistoryResponse();
              List<Map<String, Object>> histories = getAll()
                      .stream()
                      .filter(history -> history.getTrainingRegister().getTraining().equals(training))
                      .filter(history -> history.getTrainingRegister().getTrainee().equals(loggedInEmp))
                      .map(history -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("status", history.getStatus());
                        map.put("notes", history.getNotes());
                        map.put("date", history.getCreatedAt());
                        return map;
                      })
                      .collect(Collectors.toList());
              historyResponse.setTrainee(loggedInEmp);
              historyResponse.setTraining(training);
              historyResponse.setHistories(histories);
              return historyResponse;
            })
            .filter(historyResponse -> !historyResponse.getHistories().isEmpty())
            .collect(Collectors.toList());
  }
}
