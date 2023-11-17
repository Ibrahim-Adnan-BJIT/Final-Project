package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.entity.BloodPressureRecommendation;
import com.example.ClinicalDecisionSupportSystemService.repository.BloodPressureRecommendationRepo;
import com.example.ClinicalDecisionSupportSystemService.service.BloodPressureRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BloodPressureRecommendationServiceImpl implements BloodPressureRecommendationService {
    private BloodPressureRecommendationRepo bloodPressureRecommendationRepo;
    @Override
    public void updateBloodRecommendation(BloodPressureRecommendation bloodPressureRecommendation) {
        Optional<BloodPressureRecommendation> bloodPressureRecommendation1=bloodPressureRecommendationRepo.findById(1L);

        if(bloodPressureRecommendation1.isEmpty())
        {
            //create
            bloodPressureRecommendationRepo.save(bloodPressureRecommendation);

        }
        else
        {
            //update
            bloodPressureRecommendation1.get().setNormal(bloodPressureRecommendation.getNormal());
            bloodPressureRecommendation1.get().setElevated(bloodPressureRecommendation.getElevated());
            bloodPressureRecommendation1.get().setHypertensive_crisis(bloodPressureRecommendation.getHypertensive_crisis());
            bloodPressureRecommendation1.get().setHypertension_stage_2(bloodPressureRecommendation.getHypertension_stage_2());
            bloodPressureRecommendation1.get().setHypertension_stage_1(bloodPressureRecommendation.getHypertension_stage_1());
            bloodPressureRecommendationRepo.save(bloodPressureRecommendation1.get());

        }

    }
}
