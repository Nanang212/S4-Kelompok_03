package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.TrainingRegister;
import id.co.mii.serverapp.models.dto.requests.TrainingRegisterRequest;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.models.dto.responses.TrainingRegisterResponse;
import id.co.mii.serverapp.services.TrainingRegisterService;
import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {
  private TrainingService trainingService;
  private TrainingRegisterService trainingRegisterService;

  @PostMapping(value = "/register", consumes = "multipart/form-data")
  public ResponseEntity<TrainingRegister> trainingRegistration(TrainingRegisterRequest trainingRegisterRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trainingRegisterService.create(trainingRegisterRequest));
  }

  @PostMapping(value = "/register/cancel")
  public ResponseEntity<TrainingRegister> trainingCancellation(@RequestBody TrainingRegisterRequest trainingRegisterRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trainingRegisterService.createCancellation(trainingRegisterRequest));
  }

  @PostMapping
  public ResponseEntity<Training> create(@RequestBody TrainingRequest trainingRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trainingService.create(trainingRequest));
  }

  @PostMapping("/broadcast/{id}")
  public ResponseEntity<Training> broadcast(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.broadcastEmail(id));
  }

  @GetMapping("/register/logged-in-emp/training/{id}")
  public ResponseEntity<TrainingRegister> getByTrainingAndLoggedInEmployee(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getByTrainingAndLoggedInEmployee(id));
  }

  @GetMapping(value = "/register/cancel")
  public ResponseEntity<List<TrainingRegisterResponse>> getAllTrainingCancellation() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getAllCancellationGroupByTraining());
  }

  @GetMapping(value = "/register/cancel/{id}")
  public ResponseEntity<List<Map<String, Object>>> getCancellationByIdGroupByTraining(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getCancellationByIdGroupByTraining(id));
  }

  @GetMapping("/register")
  public ResponseEntity<List<TrainingRegister>> getAllTrainingRegister() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getAll());
  }

  @GetMapping("/register/training")
  public ResponseEntity<List<TrainingRegisterResponse>> getAllTrainingRegisterByTraining() {
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

  @GetMapping("/register/{id}")
  public ResponseEntity<TrainingRegister> getTrainingRegisterById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<Training>> getAll(@RequestParam(required = false) Integer categoryId) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getAll(categoryId));
  }

  @GetMapping("/trainer/{username}")
  public ResponseEntity<List<Training>> getAllTrainingByTrainer(@PathVariable String username) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getAllByTrainer(username));
  }

  @GetMapping("/trainee/{username}")
  public ResponseEntity<List<Training>> getAllTrainingByTrainee(@PathVariable String username) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getAllByTrainee(username));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Training> getById(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getById(id));
  }

  @GetMapping("/register/attachment/{id}")
  public ResponseEntity<byte[]> getAttachmentByTrainingRegister(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_PDF)
            .body(trainingRegisterService.getAttachmentById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Training> update(@PathVariable Integer id, @RequestBody TrainingRequest trainingRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.update(id, trainingRequest));
  }

  @PutMapping("/register/{id}")
  public ResponseEntity<TrainingRegister> updateTrainingRegister(@PathVariable Integer id, @RequestBody TrainingRegisterRequest trainingRegisterRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.update(id, trainingRegisterRequest));
  }

  @DeleteMapping("/register/{id}")
  public ResponseEntity<?> deleteTrainingRegister(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingRegisterService.delete(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.delete(id));
  }
}
