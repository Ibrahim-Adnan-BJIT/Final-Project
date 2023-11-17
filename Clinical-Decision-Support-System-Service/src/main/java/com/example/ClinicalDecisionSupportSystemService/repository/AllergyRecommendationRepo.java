package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.AllergyRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AllergyRecommendationRepo extends JpaRepository<AllergyRecommendation ,Long> {
}
