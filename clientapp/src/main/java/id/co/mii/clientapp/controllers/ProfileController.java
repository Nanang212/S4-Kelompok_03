package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// TODO : Otorisasi
@Controller
@AllArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
  private EmployeeService employeeService;

  @GetMapping
  public String profileView(Model model) {
    model.addAttribute("loggedInEmployee", employeeService.getLoggedInUser());
    return "profile/index";
  }
}
