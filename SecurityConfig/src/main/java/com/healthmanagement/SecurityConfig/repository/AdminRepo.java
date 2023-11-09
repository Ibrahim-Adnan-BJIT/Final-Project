package com.healthmanagement.SecurityConfig.repository;

import com.healthmanagement.SecurityConfig.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Long> {
}
