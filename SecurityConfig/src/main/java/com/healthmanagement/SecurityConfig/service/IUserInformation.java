package com.healthmanagement.SecurityConfig.service;

import com.healthmanagement.SecurityConfig.dto.DoctorsDto;
import com.healthmanagement.SecurityConfig.dto.ProfileDto;
import com.healthmanagement.SecurityConfig.dto.SearchDoctorDto;
import com.healthmanagement.SecurityConfig.dto.UserInformationsDto;
import com.healthmanagement.SecurityConfig.entity.Speciality;

import java.util.List;

public interface IUserInformation {
    public UserInformationsDto getUserInformation(long userId);

    public ProfileDto getProfile();

    public void updateProfile(ProfileDto profileDto);
    public void updateSkills(DoctorsDto doctorsDto);

    public Long getDoctorId(long id);
    public Long getPatientId(long id);

    public String getPatientName(long patientId);

    public String getDoctorName(long doctorId);

    public List<SearchDoctorDto> getAllDoctors();

    public String getEmailForPatient(long patientId);
    public String getEmailForDoctor(long doctorId);

    List<SearchDoctorDto> getDoctorsBySpeciality(Speciality speciality);
    List<SearchDoctorDto>getDoctorsByFirstName(String name);

}
