package com.example.ClinicalDecisionSupportSystemService.service;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.entity.HealthData;
import com.example.ClinicalDecisionSupportSystemService.entity.PreviousHealthData;
import com.example.ClinicalDecisionSupportSystemService.repository.PreviousHealthDataRepo;

import java.util.List;
import java.util.Optional;

public interface HealthDataService {
    void storeHealthData(HealthDataDto healthDataDto);

    Optional<HealthData> getHealthData();

    List<PreviousHealthData>getAllHealthTrack();

}
