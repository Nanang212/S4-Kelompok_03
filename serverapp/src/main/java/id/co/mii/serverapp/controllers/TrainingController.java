package id.co.mii.serverapp.controllers;

import id.co.mii.serverapp.models.Training;
import id.co.mii.serverapp.models.dto.requests.TrainingRequest;
import id.co.mii.serverapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {
  private TrainingService trainingService;

  @PostMapping
  public ResponseEntity<Training> create(@RequestBody TrainingRequest trainingRequest) {
    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(trainingService.create(trainingRequest));
  }

  @GetMapping
  public ResponseEntity<List<Training>> getAll() {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.getAll());
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

  @PutMapping("/{id}")
  public ResponseEntity<Training> update(@PathVariable Integer id, @RequestBody TrainingRequest trainingRequest) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.update(id, trainingRequest));
  }

  @PutMapping("/register/{trainingId}/trainee/{traineeId}")
  public ResponseEntity<Training> registerTrainee(@PathVariable Integer trainingId, @PathVariable Integer traineeId) {
    return ResponseEntity
            .status(HttpStatus.OK)
            .body(trainingService.registerTraineeToTraining(trainingId, traineeId));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    trainingService.delete(id);
    return ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(null);
  }
}
