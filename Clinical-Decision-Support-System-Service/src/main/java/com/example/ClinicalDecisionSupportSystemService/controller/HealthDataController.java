package com.example.ClinicalDecisionSupportSystemService.controller;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.entity.HealthData;
import com.example.ClinicalDecisionSupportSystemService.service.HealthDataService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/healthdata")
@AllArgsConstructor
public class HealthDataController {

    private HealthDataService healthDataService;
    @PutMapping("/update/record")
    public ResponseEntity<String> updateRecord(@RequestBody HealthDataDto healthDataDto)
    {
        healthDataService.storeHealthData(healthDataDto);
        return new ResponseEntity<>("Health Record Updated", HttpStatus.OK);
    }

    @GetMapping("/get/healthData")
    public ResponseEntity<Object> getHealthData()
    {
        Optional<HealthData> healthData=healthDataService.getHealthData();
        return new ResponseEntity<>(healthData.get(),HttpStatus.OK);
    }
}
