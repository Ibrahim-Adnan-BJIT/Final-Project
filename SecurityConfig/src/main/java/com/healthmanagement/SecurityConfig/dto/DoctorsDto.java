package com.healthmanagement.SecurityConfig.dto;

import com.healthmanagement.SecurityConfig.entity.Speciality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class DoctorsDto {
    private long doctorId;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;
    private String qualification;
}
