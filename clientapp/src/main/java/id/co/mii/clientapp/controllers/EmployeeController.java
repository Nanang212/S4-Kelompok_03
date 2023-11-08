package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.services.EmployeeService;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private EmployeeService employeeService;

    @GetMapping
    public String getAll(Model model) {
//        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("isActive", "employee");
        return "employee/index";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable Integer id, Model model) {
        Employee employe = employeeService.getById(id);
       model.addAttribute("employee", employe);
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
}
