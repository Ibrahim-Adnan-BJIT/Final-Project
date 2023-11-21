package com.healthmanagement.SecurityConfig.dto;

import com.healthmanagement.SecurityConfig.entity.Speciality;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.jmx.export.annotation.ManagedNotifications;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchDoctorDto {
    private long doctorId;
    private String firstName;
    private String lastName;
    @Enumerated(EnumType.STRING)
    private Speciality speciality;

}
