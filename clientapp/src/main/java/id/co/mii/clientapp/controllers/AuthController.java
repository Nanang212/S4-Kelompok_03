package id.co.mii.clientapp.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.models.dto.request.LoginRequest;
import id.co.mii.clientapp.services.AuthService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    private AuthenticationSessionUtil session;

     @GetMapping("/login")
    public String loginView(LoginRequest loginRequest) {
        if (session.authentication() instanceof AnonymousAuthenticationToken) {
            return "auth/login";
        }
         return "redirect:/training";
    }

    @PostMapping("/login")
    public String login(LoginRequest loginRequest) {
        if (!authService.login((loginRequest))) {
            return "redirect:/login?error=true";
        }
       return "redirect:/training";
    }

  @GetMapping("/register")
  public String registerView(EmployeeRequest employeeRequest, @RequestParam(required = false) String error, Model model) {
    model.addAttribute("message", error);
    return "auth/register";
  }

  @PostMapping("/register")
  public String register(EmployeeRequest employeeRequest, Model model) {
    try {
      authService.registration(employeeRequest);
      return "redirect:/auth/login";
    } catch (HttpClientErrorException e) {
      e.printStackTrace();
      return "redirect:/auth/register?error=" + e.getMessage();
    }

  }

  @GetMapping("/change-password")
  public String changePasswordview(Model model) {
      model.addAttribute("title", "MCC 81");
      return "auth/change-password";
  }
}
