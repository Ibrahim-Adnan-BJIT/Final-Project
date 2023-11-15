package com.example.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resource_allocation")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ResourceAllocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long resourceId;
    private long doctorId;
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private List<MedicalEquipment> equipments=new ArrayList<>();
}
