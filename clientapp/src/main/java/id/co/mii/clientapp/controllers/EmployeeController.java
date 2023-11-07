package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.request.EmployeeRequest;
import id.co.mii.clientapp.services.AuthService;
// import id.co.mii.clientapp.services.EmployeeService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

  @GetMapping
  public String getAll(Model model) {
      model.addAttribute("isActive", "employee");
      return "employee/index";
  }
//   private EmployeeService employeeService;
// private AuthService authService;

//   @GetMapping
//   public String getAll(Model model) {
//     model.addAttribute("employee", employeeService.getAll());
//     return "employee/index";
//   }

//   @GetMapping("/create")
//   public String createView(Employee employee) {
//     return "employee/create-form";
//   }

//   @PostMapping
//   public String create(EmployeeRequest employeeRequest) {
//     authService.registration(employeeRequest);
//     return "redirect:/employee";
//   }

//   @GetMapping("/{id}")
//   public String getById(@PathVariable Integer id, Model model) {
//     model.addAttribute("employee", employeeService.getById(id));
//     return "employee/detail";
//   }

//   @GetMapping("/update/{id}")
//   public String updateView(@PathVariable Integer id, Model model) {
//     model.addAttribute("employee", employeeService.getById(id));
//     return "employee/update-form";
//   }

//   @PutMapping("/{id}")
//   public String update(@PathVariable Integer id, EmployeeRequest employeeRequest) {
//     employeeService.update(id, employeeRequest);
//     return "redirect:/employee";
//   }

//   @DeleteMapping("/{id}")
//   public String delete(@PathVariable Integer id) {
//     employeeService.delete(id);
//     return "redirect:/employee";
//   }
}