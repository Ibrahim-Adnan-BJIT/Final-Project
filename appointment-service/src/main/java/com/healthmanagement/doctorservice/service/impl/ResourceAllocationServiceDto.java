package com.healthmanagement.doctorservice.service.impl;

import com.healthmanagement.doctorservice.dto.ResourceDto;
import com.healthmanagement.doctorservice.entity.ResourceAllocation;
import com.healthmanagement.doctorservice.exception.InvalidRoomNumber;
import com.healthmanagement.doctorservice.repository.ResourceAllocationRepo;
import com.healthmanagement.doctorservice.service.ResourceAllocationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.spi.RegisterableService;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ResourceAllocationServiceDto implements ResourceAllocationService {
    private ResourceAllocationRepo resourceAllocationRepo;
    @Override
    public void allocateResource(ResourceDto resourceDto) {
        List<ResourceAllocation> resourceAllocationList=resourceAllocationRepo.findAll();

        for(ResourceAllocation r1: resourceAllocationList)
        {
            if(r1.getRoomNumber()==resourceDto.getRoomNumber())
            {
                throw new InvalidRoomNumber("Please Enter an Unique Room Number");
            }
        }
        ResourceAllocation resourceAllocation=new ResourceAllocation();
        resourceAllocation.setAllocationId(resourceDto.getAllocationId());
        resourceAllocation.setDoctorId(resourceDto.getDoctorId());
        resourceAllocation.setRoomNumber(resourceDto.getRoomNumber());
        List<String>equipments=new ArrayList<>();
        equipments.addAll(resourceDto.getEquipments());
        resourceAllocation.setEquipments(equipments);
        resourceAllocationRepo.save(resourceAllocation);

    }
}
