package id.co.mii.serverapp.services;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.History;
import id.co.mii.serverapp.models.Role;
import id.co.mii.serverapp.services.base.BaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class HistoryService extends BaseService<History, Integer> {
  private EmployeeService employeeService;
  private RoleService roleService;

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
}
