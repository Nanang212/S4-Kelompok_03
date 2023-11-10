package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.RoleService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.services.EmployeeService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
  private EmployeeService employeeService;
  private AuthenticationSessionUtil authenticationSessionUtil;

  @GetMapping
  public String getAll(Model model) {
    List<String> roles = authenticationSessionUtil
            .authentication()
            .getAuthorities()
            .stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    model.addAttribute("loggedInEmployee", employeeService.getLoggedInUser());
    model.addAttribute("isActive", "employee");
    return "employee/index";
  }

  @GetMapping("/{id}")
  public String getById(@PathVariable Integer id, Model model) {
    Employee employee = employeeService.getById(id);
    model.addAttribute("employee", employee);
    model.addAttribute("isActive", "employee");
    return "employee/detail";
  }

  @GetMapping("/trainer")
  public String getAllTrainer(Model model) {
    model.addAttribute("isActiveTrainer", true);
    return "redirect:/employee?role=" + "trainer";
  }

  @GetMapping("/trainee")
  public String getAllTrainee(Model model) {
    model.addAttribute("isActiveTrainee", true);
    return "redirect:/employee?role=" + "trainee";
  }

  @GetMapping("/update/{id}")
  public String updateView(@PathVariable Integer id, Model model) {
//    model.addAttribute("loggedInEmployee", employeeService.getLoggedInUser());
//    model.addAttribute("id", id);
    model.addAttribute("employee", employeeService.getById(id));
    return "employee/update";
  }
}
