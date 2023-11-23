package com.example.ClinicalDecisionSupportSystemService.controller;

import com.example.ClinicalDecisionSupportSystemService.entity.*;
import com.example.ClinicalDecisionSupportSystemService.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recommendation")
@AllArgsConstructor
public class RecommendationController {
    private BloodPressureRecommendationService bloodPressureRecommendationService;
    private BmiRecommendationService bmiRecommendationService;
    private DiabetesRecommendationService diabetesRecommendationService;
    private AllergyRecommendationService allergyRecommendationService;
    private RecommendationService recommendationService;

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

    @GetMapping("/getRecommendationByPatientId")
    public ResponseEntity<Recommendation> getRecommendationByPatientId()
    {
        Recommendation recommendation=recommendationService.getRecommendationByPatientId();
        return new ResponseEntity<>(recommendation,HttpStatus.OK);
    }
}
