package id.co.mii.clientapp.controllers.rest;

import java.util.List;

import id.co.mii.clientapp.models.dto.request.LoginRequest;
import id.co.mii.clientapp.models.dto.request.PasswordRequest;
import id.co.mii.clientapp.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.co.mii.clientapp.models.Employee;
import id.co.mii.clientapp.models.dto.request.EmployeeRequest;
import id.co.mii.clientapp.models.dto.response.EmployeeResponse;
import id.co.mii.clientapp.services.EmployeeService;
import id.co.mii.clientapp.utils.AuthenticationSessionUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/employee")
public class RestEmployeeController {
  private EmployeeService employeeService;
  private AuthenticationSessionUtil session;
  private AuthService authService;

  @GetMapping
  public ResponseEntity<?> getAll(@RequestParam(required = false) String role) {
    try {
      if (role != null) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeService.getAllBy(role));
      }
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(employeeService.getAll());
    } catch (HttpClientErrorException exception) {
      return ResponseEntity
              .status(exception.getRawStatusCode())
              .headers(exception.getResponseHeaders())
              .body(exception.getResponseBodyAsString());
    }
  }

  @GetMapping("/{id}")
  public Employee getById(@PathVariable Integer id) {
    return employeeService.getById(id);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody EmployeeRequest employeeRequest) {
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(employeeService.create(employeeRequest));
    } catch (HttpClientErrorException exception) {
      return ResponseEntity
              .status(exception.getRawStatusCode())
              .headers(exception.getResponseHeaders())
              .body(exception.getResponseBodyAsString());
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
    try {
      return ResponseEntity
              .status(HttpStatus.OK)
              .body(employeeService.update(id, employeeRequest));
    } catch (HttpClientErrorException exception) {
      return ResponseEntity
              .status(exception.getRawStatusCode())
              .headers(exception.getResponseHeaders())
              .body(exception.getResponseBodyAsString());
    }
  }

  @PutMapping("/change-password")
  public ResponseEntity<?> changePassword(@RequestBody PasswordRequest passwordRequest) {
    Employee employeeResponse = null;
    try {
      employeeResponse = employeeService.changePassword(passwordRequest);
      String username = session.authentication().getPrincipal().toString();
      authService.login(new LoginRequest(username, passwordRequest.getNewPassword()));
    } catch (HttpClientErrorException exception) {
      return ResponseEntity
              .status(exception.getRawStatusCode())
              .headers(exception.getResponseHeaders())
              .body(exception.getResponseBodyAsString());
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(employeeResponse);
  }

  @DeleteMapping("/{id}")
  public EmployeeResponse delete(@PathVariable Integer id) {
    return employeeService.delete(id);
  }
}
