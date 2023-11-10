package com.healthmanagement.doctorservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.text.DateFormat;
import java.time.LocalDate;

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


}
