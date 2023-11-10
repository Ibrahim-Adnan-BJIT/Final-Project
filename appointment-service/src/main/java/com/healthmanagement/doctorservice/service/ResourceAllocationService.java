package com.healthmanagement.doctorservice.service;

import com.healthmanagement.doctorservice.dto.ResourceDto;

public interface ResourceAllocationService {

    void allocateResource(ResourceDto resourceDto);
}
