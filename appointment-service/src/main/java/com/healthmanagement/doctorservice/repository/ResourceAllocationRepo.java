package com.healthmanagement.doctorservice.repository;

import com.healthmanagement.doctorservice.entity.ResourceAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceAllocationRepo extends JpaRepository<ResourceAllocation,Long> {
}
