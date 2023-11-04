package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.dto.requests.EmployeeRequest;
import id.co.mii.serverapp.models.dto.requests.LoginRequest;
import id.co.mii.serverapp.models.dto.responses.LoginResponse;
import id.co.mii.serverapp.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {
  private AuthService authService;

  @PostMapping("/registration")
  public ResponseEntity<Employee> registration(@RequestBody EmployeeRequest employeeRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.registration(employeeRequest));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.login(loginRequest));
  }
}
