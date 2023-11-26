package com.example.ClinicalDecisionSupportSystemService.repository;

import com.example.ClinicalDecisionSupportSystemService.entity.PreviousHealthData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PreviousHealthDataRepo extends JpaRepository<PreviousHealthData,Long> {
}
