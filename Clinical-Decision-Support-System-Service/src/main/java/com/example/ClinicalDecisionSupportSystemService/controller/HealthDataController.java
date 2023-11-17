package com.example.ClinicalDecisionSupportSystemService.controller;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.service.HealthDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/healthdata")
@AllArgsConstructor
public class HealthDataController {

    private HealthDataService healthDataService;
    @PutMapping("/update/record")
    public ResponseEntity<String> updateRecord(@RequestBody HealthDataDto healthDataDto)
    {
        healthDataService.storeHealthData(healthDataDto);
        return new ResponseEntity<>("Health Record Updated", HttpStatus.ACCEPTED);
    }
}
