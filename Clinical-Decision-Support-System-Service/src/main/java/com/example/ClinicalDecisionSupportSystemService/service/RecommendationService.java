package com.example.ClinicalDecisionSupportSystemService.service;

import com.example.ClinicalDecisionSupportSystemService.entity.Recommendation;

public interface RecommendationService {

    Recommendation getRecommendationByPatientId();
}
