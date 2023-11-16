package com.example.appointmentservice.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppointmentDto {
    private long appointmentId;
    private long doctorId;
    private long patientId;
    private String location;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String type;
    private String roomNumber;
    private String status;
}
