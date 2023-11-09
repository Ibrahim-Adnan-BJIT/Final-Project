package com.healthmanagement.SecurityConfig.dto;

import com.healthmanagement.SecurityConfig.entity.Gender;
import lombok.*;

import java.time.LocalDate;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class ProfileDto {

    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String email;
    private Gender gender;
    private String number;
}
