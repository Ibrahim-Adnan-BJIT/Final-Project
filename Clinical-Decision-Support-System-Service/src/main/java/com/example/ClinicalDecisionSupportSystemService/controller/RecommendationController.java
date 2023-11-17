package com.example.ClinicalDecisionSupportSystemService.controller;

import com.example.ClinicalDecisionSupportSystemService.entity.AllergyRecommendation;
import com.example.ClinicalDecisionSupportSystemService.entity.BloodPressureRecommendation;
import com.example.ClinicalDecisionSupportSystemService.entity.BmiRecommendation;
import com.example.ClinicalDecisionSupportSystemService.entity.DiabetesRecommendation;
import com.example.ClinicalDecisionSupportSystemService.service.AllergyRecommendationService;
import com.example.ClinicalDecisionSupportSystemService.service.BloodPressureRecommendationService;
import com.example.ClinicalDecisionSupportSystemService.service.BmiRecommendationService;
import com.example.ClinicalDecisionSupportSystemService.service.DiabetesRecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/recommendation")
@AllArgsConstructor
public class RecommendationController {
    private BloodPressureRecommendationService bloodPressureRecommendationService;
    private BmiRecommendationService bmiRecommendationService;
    private DiabetesRecommendationService diabetesRecommendationService;
    private AllergyRecommendationService allergyRecommendationService;

    @PutMapping("/update/blood")
    public ResponseEntity<String> updateBlood(@RequestBody BloodPressureRecommendation bloodPressureRecommendation)
    {
        bloodPressureRecommendationService.updateBloodRecommendation(bloodPressureRecommendation);
        return new ResponseEntity<>("Record inserted successfully", HttpStatus.ACCEPTED);
    }
    @PutMapping("/update/bmi")
    public ResponseEntity<String> updateBmi(@RequestBody BmiRecommendation bmiRecommendation)
    {
       bmiRecommendationService.updateBmiRecom(bmiRecommendation);
        return new ResponseEntity<>("Record inserted successfully", HttpStatus.ACCEPTED);
    }
    @PutMapping("/update/diabetes")
    public ResponseEntity<String> updateDiabetes(@RequestBody DiabetesRecommendation diabetesRecommendation)
    {
        diabetesRecommendationService.updateDiabetesRecom(diabetesRecommendation);
        return new ResponseEntity<>("Record inserted successfully", HttpStatus.ACCEPTED);
    }

    @PutMapping("/update/allergy")
    public ResponseEntity<String> updateAllergy(@RequestBody AllergyRecommendation allergyRecommendation)
    {
        allergyRecommendationService.updateAllergyRecom(allergyRecommendation);
        return new ResponseEntity<>("Record inserted successfully", HttpStatus.ACCEPTED);
    }
}
