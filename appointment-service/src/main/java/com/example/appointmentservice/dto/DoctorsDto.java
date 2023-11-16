package com.example.appointmentservice.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class DoctorsDto {
    private long doctorId;
    private String speciality;
    private String qualification;
}
