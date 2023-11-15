package com.healthmanagement.SecurityConfig.controller;


import com.healthmanagement.SecurityConfig.dto.DoctorsDto;
import com.healthmanagement.SecurityConfig.dto.ProfileDto;
import com.healthmanagement.SecurityConfig.dto.UserInformationsDto;
import com.healthmanagement.SecurityConfig.service.IUserInformation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/user")
@AllArgsConstructor
public class UserController {

    private  IUserInformation userService;
    @GetMapping("/user-information/{userId}")
    public UserInformationsDto getUserInformation(@PathVariable long userId) {
        return userService.getUserInformation(userId);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile()
    {
        ProfileDto profileDto=userService.getProfile();
        return new ResponseEntity<>(profileDto, HttpStatus.FOUND);
    }

    @PutMapping("/update/profile")
    public ResponseEntity<String> updateProfile(@RequestBody ProfileDto profileDto)
    {
        userService.updateProfile(profileDto);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.ACCEPTED);
    }

    @PutMapping("/skills")
    public  ResponseEntity<?> checkingSkills(@RequestBody DoctorsDto doctorsDto)
    {
        userService.updateSkills(doctorsDto);
        return new ResponseEntity<>("Updated Successfully",HttpStatus.ACCEPTED);
    }

    @GetMapping("/getDoctor/{id}")
    public Long getDoctor(@PathVariable long id)
    {
        return userService.getDoctorId(id);
    }

    @GetMapping("/getPatient/{id}")
    public Long getPatient(@PathVariable long id)
    {
        return userService.getPatientId(id);
    }
}