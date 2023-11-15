package com.example.appointmentservice.dto;

import com.example.appointmentservice.entity.MedicalEquipment;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ResourceAllocationDto {
    private String roomNumber;
    @Enumerated(EnumType.STRING)
    private List<MedicalEquipment> equipments=new ArrayList<>();
}
