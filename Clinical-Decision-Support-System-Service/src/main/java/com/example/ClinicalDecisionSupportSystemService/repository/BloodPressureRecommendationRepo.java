package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.BloodPressureRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodPressureRecommendationRepo extends JpaRepository<BloodPressureRecommendation,Long> {
}
