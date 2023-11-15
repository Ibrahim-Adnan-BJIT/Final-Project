package com.example.appointmentservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SlotDto {
    private LocalDate date;
    private LocalTime startTime;
    private String type;
}
