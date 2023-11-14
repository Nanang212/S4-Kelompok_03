package id.co.mii.clientapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import id.co.mii.clientapp.models.Survey;
import id.co.mii.clientapp.services.SurveyService;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Controller
@RequestMapping("/survey")
public class SurveyController {
    private SurveyService surveyService;

    @GetMapping
    public String getAll(Model model) {
        // model.addAttribute("title", "MCC 81");
        return "training/survey";
    }

    @GetMapping("/create/{trainingRegisterId}")
    public String createView(@PathVariable("trainingRegisterId") Integer trainingRegisterId, Model model) {
        model.addAttribute("trainingRegisterId", trainingRegisterId);
        return "training/survey";
    }

    @PostMapping
    public String create(Survey survey, Integer trainingRegisterId) {
        surveyService.create(survey, trainingRegisterId);
        return "redirect:/training/attend";
    }
}
