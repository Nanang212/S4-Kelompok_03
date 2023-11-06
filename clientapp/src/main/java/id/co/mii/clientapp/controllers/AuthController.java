package id.co.mii.clientapp.controllers;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.request.LoginRequest;
import id.co.mii.clientapp.request.RegisterRequest;
import id.co.mii.clientapp.request.RegistrationRequest;
import id.co.mii.clientapp.services.AuthService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthController {
  private AuthService authService;
  private AuthenticationSessionUtil session;
  private EmailRegistrationService emailRegistrationService;

  @GetMapping("/login")
  public String loginView(LoginRequest loginRequest) {
    // Authentication auth = SecurityContextHolder
    // .getContext()
    // .getAuthentication();

    if (session.authentication() instanceof AnonymousAuthenticationToken) {
      return "auth/login";
    }
    return "redirect:/dashboard";
  }

  @PostMapping("/login")
  public String login(LoginRequest loginRequest) {
    System.out.println(loginRequest.toString());
    if (!authService.login((loginRequest))) {
      return "redirect:/login?error=true";
    }
    return "redirect:/dashboard";
  }

    @GetMapping("/register")
    public String registerView() {
        return "auth/register";
    }

    @GetMapping("/verify")
    String verify(@RequestParam(name = "token", required = false) String token, Model model) {
        return emailRegistrationService.isVerifyUser(token);
    }

  // @GetMapping("/register")
  // public String registerView(RegistrationRequest registrationRequest) {
  //   return "auth/register1";
  // }

  // @PostMapping("/registration")
  // public String register(RegisterRequest registrationRequest, Model model) {
    // Employee employee = authService.registration(registrationRequest);

    // if (!employee.equals(new Employee())) {
    // return "redirect:";
    // }
    // return "auth/login";
  //   authService.registration(registrationRequest);
  //   return "redirect:/login";
  // }

  // @GetMapping("/register-success")
  // public ModelAndView registerSuccessView() {
  // ModelAndView mv = new ModelAndView();
  // mv.setViewName("/auth/register-success");
  // return mv;
  // }
  // }
}