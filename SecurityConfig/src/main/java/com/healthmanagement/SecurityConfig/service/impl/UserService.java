package com.healthmanagement.SecurityConfig.service.impl;

import com.healthmanagement.SecurityConfig.dto.*;
import com.healthmanagement.SecurityConfig.entity.*;
import com.healthmanagement.SecurityConfig.exception.AuthenticationExceptions;
import com.healthmanagement.SecurityConfig.exception.ResourceNotFoundException;
import com.healthmanagement.SecurityConfig.repository.*;
import com.healthmanagement.SecurityConfig.service.IUserInformation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class UserService implements IUserInformation {
    private final UserRepository userRepository;
    private ModelMapper modelMapper;
    private final DoctorRepo doctorRepo;
    private final CategoriesRepo categoriesRepo;
    private final DoctorSpecialityRepo doctorSpecialityRepo;
    private final PatientRepo patientRepo;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, DoctorRepo doctorRepo, CategoriesRepo categoriesRepo, DoctorSpecialityRepo doctorSpecialityRepo, PatientRepo patientRepo) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.doctorRepo = doctorRepo;
        this.categoriesRepo = categoriesRepo;
        this.doctorSpecialityRepo = doctorSpecialityRepo;
        this.patientRepo = patientRepo;
    }

    public UserInformationsDto getUserInformation(long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                ()-> new ResourceNotFoundException("User", "id", userId));

        UserInformationsDto userInformation = new UserInformationsDto();
        userInformation.setUsername(user.getUsername());
        userInformation.setEmail(user.getEmail());

        return userInformation;
    }

    @Override
    public ProfileDto getProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());


        Optional<User> user =userRepository.findById(id);
        ProfileDto profileDto=new ProfileDto();
        profileDto.setFirstName(user.get().getFirstName());
        profileDto.setLastName(user.get().getLastName());
        profileDto.setEmail(user.get().getEmail());
        profileDto.setDob(user.get().getDob());
        profileDto.setNumber(user.get().getNumber());
        profileDto.setGender(user.get().getGender());

      return profileDto;
    }

    @Override
    public void updateProfile(ProfileDto profileDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Optional<User> user =userRepository.findById(id);
         user.get().setFirstName(profileDto.getFirstName());
         user.get().setLastName(profileDto.getLastName());
         user.get().setEmail(profileDto.getEmail());
         user.get().setNumber(profileDto.getNumber());
         user.get().setGender(profileDto.getGender());
         user.get().setDob(profileDto.getDob());
         userRepository.save(user.get());

    }

    @Override
    public void updateSkills(DoctorsDto doctorsDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
       Doctor doctor= doctorRepo.findByUserId(id);
       doctor.setQualification(doctorsDto.getQualification());
       doctor.setSpeciality(Speciality.valueOf(doctorsDto.getSpeciality()));
       doctorRepo.save(doctor);

        Optional<Categories> categories= Optional.ofNullable(categoriesRepo.findByCategoryName(Speciality.valueOf(doctorsDto.getSpeciality())));
        if(categories.isPresent())
        {
            DoctorSpecialities doctorSpecialities=new DoctorSpecialities();
            doctorSpecialities.setDoctorId(doctor.getDoctorId());
            doctorSpecialities.setCategories(categories.get());
            doctorSpecialityRepo.save(doctorSpecialities);
        }
        else
        {
            Categories categories1=new Categories();
            categories1.setCategoryName(Speciality.valueOf(doctorsDto.getSpeciality()));
            categoriesRepo.save(categories1);
            DoctorSpecialities doctorSpecialities=new DoctorSpecialities();
            doctorSpecialities.setDoctorId(doctor.getDoctorId());
            doctorSpecialities.setCategories(categories1);
            doctorSpecialityRepo.save(doctorSpecialities);
        }


       return;
    }

    @Override
    public Long getDoctorId(long id) {

        Doctor doctor=doctorRepo.findByUserId(id);
        return doctor.getDoctorId();
    }

    @Override
    public Long getPatientId(long id) {
        Patient patient=patientRepo.findByUserId(id);
        return patient.getPatientId();
    }

    @Override
    public String getPatientName(long patientId) {
       Patient patient=patientRepo.findById(patientId).orElseThrow(()->new AuthenticationExceptions("Invalid patientId"));

       Optional<User> user=userRepository.findById(patient.getUserId());
       return user.get().getFirstName()+" "+user.get().getLastName();

    }

    @Override
    public String getDoctorName(long doctorId) {
       Doctor doctor=doctorRepo.findById(doctorId).orElseThrow(()->new AuthenticationExceptions("Invalid doctorId"));
        Optional<User> user=userRepository.findById(doctor.getUserId());
        return user.get().getFirstName()+" "+user.get().getLastName();
    }


    @Override
    public List<SearchDoctorDto> getAllDoctors() {
       List<Doctor>doctors=doctorRepo.findAll();
       List<SearchDoctorDto>searchDoctorDtos=new ArrayList<>();
       for(Doctor doctor:doctors)
       {
           Optional<User> user=userRepository.findById(doctor.getUserId());
           String firstname=user.get().getFirstName();
           String lastName=user.get().getLastName();
           SearchDoctorDto searchDoctorDto=new SearchDoctorDto();
           searchDoctorDto.setDoctorId(doctor.getDoctorId());
           searchDoctorDto.setFirstName(firstname);
           searchDoctorDto.setLastName(lastName);
           searchDoctorDto.setSpeciality(doctor.getSpeciality());
           searchDoctorDtos.add((searchDoctorDto));
       }
       return searchDoctorDtos;
    }

    @Override
    public String getEmailForPatient(long patientId) {
        Patient patient=patientRepo.findById(patientId).orElseThrow(()->new AuthenticationExceptions("Invalid patientId"));

        Optional<User> user=userRepository.findById(patient.getUserId());
        return user.get().getEmail();
    }

    @Override
    public String getEmailForDoctor(long doctorId) {
        Doctor doctor=doctorRepo.findById(doctorId).orElseThrow(()->new AuthenticationExceptions("Invalid doctorId"));
        Optional<User> user=userRepository.findById(doctor.getUserId());
        return user.get().getEmail();
    }

    @Override
    public List<SearchDoctorDto> getDoctorsBySpeciality(Speciality speciality) {
        List<Doctor>doctors=doctorRepo.findAll();
        List<SearchDoctorDto>searchDoctorDtos=new ArrayList<>();
        for(Doctor doctor:doctors)
        {
            if(doctor.getSpeciality().equals(speciality) && doctor.getSpeciality()!=null) {
                Optional<User> user = userRepository.findById(doctor.getUserId());
                String firstname = user.get().getFirstName();
                String lastName = user.get().getLastName();
                SearchDoctorDto searchDoctorDto = new SearchDoctorDto();
                searchDoctorDto.setDoctorId(doctor.getDoctorId());
                searchDoctorDto.setFirstName(firstname);
                searchDoctorDto.setLastName(lastName);
                searchDoctorDto.setSpeciality(doctor.getSpeciality());
                searchDoctorDtos.add((searchDoctorDto));
            }
        }
        return searchDoctorDtos;
    }

    @Override
    public List<SearchDoctorDto> getDoctorsByFirstName(String name) {
        List<Doctor>doctors=doctorRepo.findAll();
        List<SearchDoctorDto>searchDoctorDtos=new ArrayList<>();
        for(Doctor doctor:doctors)
        {
                Optional<User> user = userRepository.findById(doctor.getUserId());
                String firstname = user.get().getFirstName();
                String lastName = user.get().getLastName();
                if(firstname.equals(name)) {
                    SearchDoctorDto searchDoctorDto = new SearchDoctorDto();
                    searchDoctorDto.setDoctorId(doctor.getDoctorId());
                    searchDoctorDto.setFirstName(firstname);
                    searchDoctorDto.setLastName(lastName);
                    searchDoctorDto.setSpeciality(doctor.getSpeciality());
                    searchDoctorDtos.add((searchDoctorDto));
                }

        }
        return searchDoctorDtos;
    }

    @Override
    public List<UserInfoDto> getAllUser() {
        List<User> users=userRepository.findAll();
       return users.stream().map((todo) -> modelMapper.map(todo, UserInfoDto.class))
                .collect(Collectors.toList());
    }

}



