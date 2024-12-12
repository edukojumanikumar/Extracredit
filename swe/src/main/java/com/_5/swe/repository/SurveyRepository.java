package com._5.swe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com._5.swe.entity.survey;

@Repository
public interface SurveyRepository extends JpaRepository<survey, Long> {
    // You can add custom query methods here if needed
}
