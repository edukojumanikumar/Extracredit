package com._5.swe.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._5.swe.entity.survey;
import com._5.swe.repository.SurveyRepository;

@Service
public class SurveyService {
    
    @Autowired
    private SurveyRepository surveyRepository;

    // Method to get all surveys
    public List<survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Method to get a survey by ID
    public Optional<survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    // Method to save a new survey
    public survey saveSurvey(survey survey) {
        return surveyRepository.save(survey);
    }

    // Method to update an existing survey
    public survey updateSurvey(Long id, survey surveyDetails) {
        if (surveyRepository.existsById(id)) {
            surveyDetails.setId(id);
            return surveyRepository.save(surveyDetails);
        } else {
            throw new RuntimeException("Survey not found with id " + id);
        }
    }

    // Method to delete a survey by ID
    public void deleteSurvey(Long id) {
        if (surveyRepository.existsById(id)) {
            surveyRepository.deleteById(id);
        } else {
            throw new RuntimeException("Survey not found with id " + id);
        }
    }
}