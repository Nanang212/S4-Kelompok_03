package id.co.mii.clientapp.controllers.rest;

import id.co.mii.clientapp.models.Survey;
import id.co.mii.clientapp.services.SurveyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/survey")
public class RestSurveyController {
  private SurveyService surveyService;

  @GetMapping
  public List<Survey> getAll() {
    return surveyService.getAll();
  }
}
