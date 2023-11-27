package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.HealthData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthDataRepo extends JpaRepository<HealthData,Long> {

    HealthData findByPatientId(long id);
}
