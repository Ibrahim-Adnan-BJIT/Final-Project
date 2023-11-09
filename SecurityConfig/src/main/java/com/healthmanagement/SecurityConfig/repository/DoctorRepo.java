package com.healthmanagement.SecurityConfig.repository;

import com.healthmanagement.SecurityConfig.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor,Long> {

    Doctor findByUserId(long id);
}
