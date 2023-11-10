package com.healthmanagement.doctorservice.entity;

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
    private long allocationId;
    private long doctorId;
    private long roomNumber;
    private List<String> equipments=new ArrayList<>();
}
