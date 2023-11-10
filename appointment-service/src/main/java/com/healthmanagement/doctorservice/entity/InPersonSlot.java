package com.healthmanagement.doctorservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OnsiteSlot")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class InPersonSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long onsiteId;
    private long doctorId;
    private LocalDate appointDate;
    private long slot;
    private long capacity;
    @OneToMany(mappedBy = "inpersonslot", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PatientRecord> patientRecords=new ArrayList<>();


}
