package com.healthmanagement.SecurityConfig.dto;

import com.healthmanagement.SecurityConfig.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Setter
@Getter
public class RegisterDto {
    @NotEmpty
    @Size(min = 2, message = " name should have at least 2 character")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = " name should have at least 2 character")
    private String lastName;

    @NotEmpty(message = "Password should not be null or empty")
    @Size(min = 5, message = "Password must have minimum 5 character")
    private String password;

    @NotEmpty(message = "Email should not be null or empty")
    @Email
    private String email;

    @NotEmpty(message = "Cant be Null")
    private String gender;

    @NotEmpty(message = "Role should not be null or empty")
    private Role role;
}
