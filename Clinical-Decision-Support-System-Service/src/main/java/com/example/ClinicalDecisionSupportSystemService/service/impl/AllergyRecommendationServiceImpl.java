package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.entity.AllergyRecommendation;
import com.example.ClinicalDecisionSupportSystemService.repository.AllergyRecommendationRepo;
import com.example.ClinicalDecisionSupportSystemService.service.AllergyRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AllergyRecommendationServiceImpl implements AllergyRecommendationService {
    private AllergyRecommendationRepo allergyRecommendationRepo;
    @Override
    public void updateAllergyRecom(AllergyRecommendation allergyRecommendation) {
        Optional<AllergyRecommendation> allergyRecommendation1=allergyRecommendationRepo.findById(1L);
        if(allergyRecommendation1.isEmpty())
        {
            allergyRecommendationRepo.save(allergyRecommendation);
        }
        else
        {
            allergyRecommendation1.get().setEgg(allergyRecommendation.getEgg());
            allergyRecommendation1.get().setGluten(allergyRecommendation.getGluten());
            allergyRecommendation1.get().setPeanut(allergyRecommendation.getPeanut());
            allergyRecommendation1.get().setSoy(allergyRecommendation.getSoy());
            allergyRecommendation1.get().setTree_nut(allergyRecommendation.getTree_nut());
            allergyRecommendation1.get().setTree_nut(allergyRecommendation.getTree_nut());
            allergyRecommendation1.get().setLactose(allergyRecommendation.getLactose());
            allergyRecommendationRepo.save(allergyRecommendation1.get());
        }
    }
}
