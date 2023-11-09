package com.healthmanagement.SecurityConfig.repository;

import com.healthmanagement.SecurityConfig.entity.Categories;
import com.healthmanagement.SecurityConfig.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepo extends JpaRepository<Categories,Long> {

    Categories findByCategoryName(Speciality speciality);
}
