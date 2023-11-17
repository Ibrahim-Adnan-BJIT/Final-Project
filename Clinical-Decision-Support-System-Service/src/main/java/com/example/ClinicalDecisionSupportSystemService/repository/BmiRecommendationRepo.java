package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.BmiRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BmiRecommendationRepo extends JpaRepository<BmiRecommendation,Long> {
}
