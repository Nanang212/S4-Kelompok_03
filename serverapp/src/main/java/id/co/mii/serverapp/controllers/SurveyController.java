package id.co.mii.serverapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import id.co.mii.serverapp.models.Survey;
import id.co.mii.serverapp.services.SurveyService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/survey")
@AllArgsConstructor
public class SurveyController {
    private SurveyService surveyService;

    @GetMapping
    public ResponseEntity<List<Survey>> getAll() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(surveyService.getAll());
    }

    @PostMapping
    public ResponseEntity<Survey> create(@RequestBody Survey survey) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(surveyService.create(survey));
    }
}
