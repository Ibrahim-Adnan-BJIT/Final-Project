package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.ResourceAllocationDto;

public interface ResourceAllocationService {

    public void createResource(ResourceAllocationDto resourceAllocationDto,long doctorId);
}
