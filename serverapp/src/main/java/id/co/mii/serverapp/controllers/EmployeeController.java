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
@PreAuthorize("hasAnyRole('ADMIN', 'TRAINER', 'TRAINEE')")
public class EmployeeController {
  private EmployeeService employeeService;
  private AuthService authService;

  @PreAuthorize("hasAuthority('CREATE_ADMIN')")
  @PostMapping
  public ResponseEntity<Employee> create(@RequestBody EmployeeRequest employeeRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(authService.registration(employeeRequest));
  }

  @PreAuthorize("hasAuthority('READ_ADMIN')")
  @GetMapping
  public ResponseEntity<List<Employee>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getAll());
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_TRAINER')")
  @GetMapping("/trainer")
  public ResponseEntity<List<Employee>> getAllTrainer() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getAllByRoleId(2));
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_TRAINER', 'READ_TRAINEE')")
  @GetMapping("/trainee")
  public ResponseEntity<List<Employee>> getAllTrainee() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getAllByRoleId(3));
  }

  @PreAuthorize("hasAnyAuthority('READ_ADMIN', 'READ_TRAINER', 'READ_TRAINEE')")
  @GetMapping("/current")
  public ResponseEntity<Employee> getLoggedInEmp() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getLoggedInEmployee());
  }

  @PreAuthorize("hasAuthority('READ_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<Employee> getById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.getById(id));
  }

  @PreAuthorize("hasAuthority('UPDATE_ADMIN')")
  @PutMapping("/{id}")
  public ResponseEntity<Employee> update(@PathVariable Integer id, @RequestBody EmployeeRequest employeeRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.update(id, employeeRequest));
  }

  @PreAuthorize("hasAuthority('DELETE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Employee> delete(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(employeeService.delete(id));
  }
}
