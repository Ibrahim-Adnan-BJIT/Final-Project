package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.ResourceAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceAllocationRepo extends JpaRepository<ResourceAllocation,Long> {

    ResourceAllocation findByDoctorId(long id);
}
