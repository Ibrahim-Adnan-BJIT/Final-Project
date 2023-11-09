package com.healthmanagement.SecurityConfig.repository;

import com.healthmanagement.SecurityConfig.entity.DoctorSpecialities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorSpecialityRepo extends JpaRepository<DoctorSpecialities,Long> {

}
