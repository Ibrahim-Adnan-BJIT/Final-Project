package com.example.ClinicalDecisionSupportSystemService.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "progress_track")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Progress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long goalId;
    private long patientId;
    private String description;
    private int progression;

}
