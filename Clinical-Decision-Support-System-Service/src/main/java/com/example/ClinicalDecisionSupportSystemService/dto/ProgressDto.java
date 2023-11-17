package com.example.ClinicalDecisionSupportSystemService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ProgressDto {
    private String description;
    private int progression;
}
