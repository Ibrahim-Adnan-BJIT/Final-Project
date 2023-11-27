package com.example.appointmentservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchDto {
    private long doctorId;
    private String doctorName;
    private String Speciality;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;
    private String type;
}
