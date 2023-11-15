package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.ResourceAllocationDto;
import com.example.appointmentservice.entity.ResourceAllocation;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.ResourceAllocationRepo;
import com.example.appointmentservice.service.ResourceAllocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

    private ResourceAllocationRepo resourceAllocationRepo;
    @Override
    public void createResource(ResourceAllocationDto resourceAllocationDto,long doctorId) {
        ResourceAllocation resourceAllocation=new ResourceAllocation();
        resourceAllocation.setDoctorId(doctorId);
        resourceAllocation.setEquipments(resourceAllocationDto.getEquipments());
        List<ResourceAllocation>resourceAllocations=resourceAllocationRepo.findAll();
        for(ResourceAllocation resourceAllocation1: resourceAllocations)
        {
            if(resourceAllocation1.getRoomNumber().equals(resourceAllocationDto.getRoomNumber()))
            {
                throw new InvalidRequestException("Room is Already Booked");
            }
        }
        resourceAllocation.setRoomNumber(resourceAllocationDto.getRoomNumber());
        resourceAllocationRepo.save(resourceAllocation);
    }
}
