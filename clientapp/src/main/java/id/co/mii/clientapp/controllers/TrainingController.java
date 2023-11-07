package id.co.mii.clientapp.controllers;

import id.co.mii.clientapp.services.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/training")
public class TrainingController {
  private TrainingService trainingService;

  @GetMapping
  public String getAll(Model model) {
    model.addAttribute("trainings", trainingService.getAll());
    return "training/index";
  }
}
