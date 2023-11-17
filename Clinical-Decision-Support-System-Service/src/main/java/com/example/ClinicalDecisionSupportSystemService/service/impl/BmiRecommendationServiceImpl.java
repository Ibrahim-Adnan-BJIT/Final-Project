package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.entity.BmiRecommendation;
import com.example.ClinicalDecisionSupportSystemService.repository.BmiRecommendationRepo;
import com.example.ClinicalDecisionSupportSystemService.service.BmiRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class BmiRecommendationServiceImpl implements BmiRecommendationService {
    private BmiRecommendationRepo bmiRecommendationRepo;
    @Override
    public void updateBmiRecom(BmiRecommendation bmiRecommendation) {
        Optional<BmiRecommendation> bmiRecommendation1=bmiRecommendationRepo.findById(1L);
        if(bmiRecommendation1.isEmpty())
        {
            bmiRecommendationRepo.save(bmiRecommendation);
        }
        else
        {
            bmiRecommendation1.get().setOverweight(bmiRecommendation.getOverweight());
            bmiRecommendation1.get().setNormalWeight(bmiRecommendation.getNormalWeight());
            bmiRecommendation1.get().setUnderweight(bmiRecommendation.getUnderweight());
            bmiRecommendation1.get().setObesityClassI(bmiRecommendation.getObesityClassI());
            bmiRecommendation1.get().setObesityClassII(bmiRecommendation.getObesityClassII());
            bmiRecommendation1.get().setObesityClassIII(bmiRecommendation.getObesityClassIII());
            bmiRecommendationRepo.save(bmiRecommendation1.get());
        }
    }
}
