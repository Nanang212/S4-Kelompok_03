package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.Survey;
import id.co.mii.clientapp.models.TrainingRegister;
import id.co.mii.clientapp.services.SurveyService;
import id.co.mii.clientapp.services.TrainingRegisterService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Controller
@RequestMapping("/survey")
public class SurveyController {
    private SurveyService surveyService;
    private TrainingRegisterService trainingRegisterService;

    @GetMapping
    public String getAll() {
        // model.addAttribute("title", "MCC 81");
        return "training/listSurvey";
    }

    @GetMapping("/create/{trainingId}")
    public String createView(@PathVariable("trainingId") Integer trainingId, Model model) {
        Survey survey = new Survey();
        TrainingRegister trainingRegister = trainingRegisterService.getByTrainingId(trainingId);
        survey.setTrainingRegister(trainingRegister);
        model.addAttribute("survey", survey);
        return "training/survey";
    }

    @PostMapping
    public String create(Survey survey) {
        surveyService.create(survey);
        return "redirect:/training/attend";
    }
}
