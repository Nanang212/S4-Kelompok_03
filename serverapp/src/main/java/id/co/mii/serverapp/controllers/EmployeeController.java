package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Employee;
import id.co.mii.serverapp.models.dto.requests.EmployeeRequest;
import id.co.mii.serverapp.services.AuthService;
import id.co.mii.serverapp.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
//@PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'TRAINEE')")
@PreAuthorize("hasRole('ADMIN')")
public class EmployeeController {
  private EmployeeService employeeService;
  private AuthService authService;

  @PostMapping
  public ResponseEntity<Employee> create(@RequestBody EmployeeRequest employeeRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authService.registration(employeeRequest));
  }

  @GetMapping
  public ResponseEntity<List<Employee>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Employee> getById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.update(id, employeeRequest));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Employee> delete(@PathVariable Integer id) {
    employeeService.delete(id);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
  }
}
