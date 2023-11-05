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
}
