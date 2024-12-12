package com._5.swe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._5.swe.entity.survey;
import com._5.swe.service.SurveyService;

@RestController
@RequestMapping("/api/surveys")
@CrossOrigin(origins = "http://ab5b15ea58d074c778c1ebb5efac4e19-137443335.us-east-2.elb.amazonaws.com") // Adjust to match your frontend URL
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // Endpoint to get all surveys
    @GetMapping
    public List<survey> getAllSurveys() {
        return surveyService.getAllSurveys();
    }

    // Endpoint to get a survey by ID
    @GetMapping("/{id}")
    public ResponseEntity<survey> getSurveyById(@PathVariable Long id) {
        Optional<survey> survey = surveyService.getSurveyById(id);
        return survey.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to create a new survey
    @PostMapping
    public survey createSurvey(@RequestBody survey survey) {
        return surveyService.saveSurvey(survey);
    }

    // Endpoint to update an existing survey
    @PutMapping("/{id}")
    public ResponseEntity<survey> updateSurvey(@PathVariable Long id, @RequestBody survey surveyDetails) {
        try {
            survey updatedSurvey = surveyService.updateSurvey(id, surveyDetails);
            return ResponseEntity.ok(updatedSurvey);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to delete a survey
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSurvey(@PathVariable Long id) {
        try {
            surveyService.deleteSurvey(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
