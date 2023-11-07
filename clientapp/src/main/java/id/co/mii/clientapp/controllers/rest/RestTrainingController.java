package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Training;
import id.co.mii.clientapp.models.dto.request.TrainingRequest;
import id.co.mii.clientapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/trainings")
public class RestTrainingController {
  private TrainingService trainingService;

  @GetMapping
  public List<Training> getAll() {
    return trainingService.getAll();
  }

  @GetMapping("/{id}")
  public Training getById(@PathVariable Integer id) {
    return trainingService.getById(id);
  }

  @PostMapping
  public Training create(@RequestBody TrainingRequest regionRequest) {
    return trainingService.create(regionRequest);
  }

  @PutMapping("/{id}")
  public Training update(@PathVariable Integer id, @RequestBody TrainingRequest trainingRequest) {
    return trainingService.update(id, trainingRequest);
  }

  @DeleteMapping("/{id}")
  public Training delete(@PathVariable Integer id) {
    return trainingService.delete(id);
  }
}
