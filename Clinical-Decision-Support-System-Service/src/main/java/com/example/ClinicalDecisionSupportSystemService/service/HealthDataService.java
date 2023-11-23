package com.example.ClinicalDecisionSupportSystemService.service;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.entity.HealthData;

import java.util.Optional;

public interface HealthDataService {
    void storeHealthData(HealthDataDto healthDataDto);

    Optional<HealthData> getHealthData();

}
