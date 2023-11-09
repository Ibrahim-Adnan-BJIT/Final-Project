package com.healthmanagement.SecurityConfig.service;

import com.healthmanagement.SecurityConfig.dto.DoctorsDto;
import com.healthmanagement.SecurityConfig.dto.ProfileDto;
import com.healthmanagement.SecurityConfig.dto.UserInformationsDto;

public interface IUserInformation {
    public UserInformationsDto getUserInformation(long userId);

    public ProfileDto getProfile();

    public void updateProfile(ProfileDto profileDto);
    public void updateSkills(DoctorsDto doctorsDto);

}