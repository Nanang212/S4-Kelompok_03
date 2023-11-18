package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Training;
import id.co.mii.clientapp.models.TrainingRegister;
import id.co.mii.clientapp.models.dto.request.TrainingRegisterRequest;
import id.co.mii.clientapp.models.dto.request.TrainingRequest;
import id.co.mii.clientapp.models.dto.response.TrainingRegisterResponse;
import id.co.mii.clientapp.services.DashboardService;
import id.co.mii.clientapp.services.TrainingRegisterService;
import id.co.mii.clientapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainings")
public class RestTrainingController {
  private TrainingService trainingService;
  private TrainingRegisterService trainingRegisterService;
  private DashboardService dashboardService;

  @GetMapping
  public List<Training> getAll(@RequestParam(required = false) Integer categoryId) {
    return trainingService.getAll(categoryId);
  }

  @GetMapping("/dashboard")
  public List<Integer> getTrainingByAllMonthInYear() {
    return dashboardService.getTrainingByAllMonthInYear();
  }

  @GetMapping("/register")
  public List<TrainingRegister> getAllTrainingRegister() {
    return trainingRegisterService.getAll();
  }

  @GetMapping("/{id}")
  public Training getById(@PathVariable Integer id) {
    return trainingService.getById(id);
  }

  @GetMapping("/register/{id}")
  public TrainingRegister getTrainingRegisterById(@PathVariable Integer id) {
    return trainingRegisterService.getById(id);
  }

  @GetMapping(value = "/register/attachment/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
  public byte[] getAttachment(@PathVariable Integer id) {
    return trainingRegisterService.getAttachment(id);
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody TrainingRequest trainingRequest) {
    try {
      return ResponseEntity
          .status(HttpStatus.OK)
          .body(trainingService.create(trainingRequest));
    } catch (HttpClientErrorException exception) {
      return ResponseEntity
          .status(exception.getRawStatusCode())
          .headers(exception.getResponseHeaders())
          .body(exception.getResponseBodyAsString());
    }
  }

  @GetMapping("/register/cancel")
  public List<TrainingRegisterResponse> getAllCancellation() {
    return trainingRegisterService.getAllCancellation();
  }

  @GetMapping("/register/cancel/{id}")
  public ResponseEntity<List<Map<String, Object>>> getCancellationByIdGroupByTraining(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getCancellationByIdGroupByTraining(id));
  }

  @PostMapping("/register/cancel/{id}")
  public TrainingRegister createCancellation(@PathVariable Integer id) {
    return trainingRegisterService.createCancellation(id);
  }

  @PutMapping("/{id}")
  public Training update(@PathVariable Integer id, @RequestBody TrainingRequest trainingRequest) {
    return trainingService.update(id, trainingRequest);
  }

  @PutMapping("/register/{id}")
  public TrainingRegister updateTrainingRegister(@PathVariable Integer id,
      @RequestBody TrainingRegisterRequest trainingRegisterRequest) {
    return trainingRegisterService.update(id, trainingRegisterRequest);
  }

  @DeleteMapping("/register/{id}")
  public TrainingRegister deleteTrainingRegister(@PathVariable Integer id) {
    return trainingRegisterService.delete(id);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    trainingService.delete(id);
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new Training());
  }

  @GetMapping("/register/training")
  public ResponseEntity<List<TrainingRegisterResponse>> getAllGroupByTraining() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getAllGroupByTraining());
  }

  @GetMapping("/register/training/{id}")
  public ResponseEntity<List<Map<String, Object>>> getByIdGroupByTraining(@PathVariable Integer id) {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(trainingRegisterService.getByIdGroupByTraining(id));
  }
}
