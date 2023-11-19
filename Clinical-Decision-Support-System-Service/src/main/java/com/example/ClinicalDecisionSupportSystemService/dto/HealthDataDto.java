package com.example.ClinicalDecisionSupportSystemService.dto;

import com.example.ClinicalDecisionSupportSystemService.entity.Allergy;
import com.example.ClinicalDecisionSupportSystemService.entity.BloodPressure;
import com.example.ClinicalDecisionSupportSystemService.entity.Diabetes;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class HealthDataDto {
    private long recordId;
    private int height;
    private int weight;
    @Enumerated(EnumType.STRING)
    private BloodPressure bloodPressure;
    @Enumerated(EnumType.STRING)
    private Allergy allergy;
    @Enumerated(EnumType.STRING)
    private Diabetes diabetes;
}
