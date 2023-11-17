package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.entity.DiabetesRecommendation;
import com.example.ClinicalDecisionSupportSystemService.repository.DiabetesRecommendationRepo;
import com.example.ClinicalDecisionSupportSystemService.service.DiabetesRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DiabetesRecommendationServiceImpl implements DiabetesRecommendationService {
    private DiabetesRecommendationRepo diabetesRecommendationRepo;
    @Override
    public void updateDiabetesRecom(DiabetesRecommendation diabetesRecommendation) {
        Optional<DiabetesRecommendation> diabetesRecommendation1=diabetesRecommendationRepo.findById(1L);
        if(diabetesRecommendation1.isEmpty())
        {
            diabetesRecommendationRepo.save(diabetesRecommendation);
        }
        else
        {
            diabetesRecommendation1.get().setNormal(diabetesRecommendation.getNormal());
            diabetesRecommendation1.get().setType1(diabetesRecommendation.getType1());
            diabetesRecommendation1.get().setType2(diabetesRecommendation.getType2());
            diabetesRecommendationRepo.save(diabetesRecommendation1.get());
        }
    }
}
