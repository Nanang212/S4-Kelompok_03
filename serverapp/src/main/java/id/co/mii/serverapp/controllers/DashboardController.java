package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.dto.responses.DashboardResponse;
import id.co.mii.serverapp.services.EmployeeService;
import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {
  private EmployeeService employeeService;
  private TrainingService trainingService;

  @GetMapping
  public ResponseEntity<DashboardResponse> getDataDashboard() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(new DashboardResponse(trainingService.countTrainingInYear(), employeeService.getAllByRoleId(3).size(), employeeService.getAllByRoleId(2).size()));
  }

  @GetMapping("/training")
  public ResponseEntity<List<Integer>> getTrainingByAllMonthInYear() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getTrainingByAllMonthInYear());
  }
}
