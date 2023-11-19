package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepo extends JpaRepository<Recommendation,Long> {
    Recommendation findByPatientId(long id);
}
