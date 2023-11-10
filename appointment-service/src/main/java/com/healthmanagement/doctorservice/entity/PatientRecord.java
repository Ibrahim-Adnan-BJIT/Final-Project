package com.healthmanagement.doctorservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "patient_record")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PatientRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recordId;
    @ManyToOne
    @JoinColumn(name = "onsite_id")
    private InPersonSlot onsiteId;
    private long patientId;
    private boolean status;

}
