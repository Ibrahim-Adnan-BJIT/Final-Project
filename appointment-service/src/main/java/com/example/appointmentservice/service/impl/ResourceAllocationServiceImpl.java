package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.DoctorsDto;
import com.example.appointmentservice.dto.ResourceAllocationDto;
import com.example.appointmentservice.entity.ResourceAllocation;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.ResourceAllocationRepo;
import com.example.appointmentservice.service.ResourceAllocationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@AllArgsConstructor
public class ResourceAllocationServiceImpl implements ResourceAllocationService {

    private final WebClient webClient;

    private ResourceAllocationRepo resourceAllocationRepo;


    @Override
    public void createResource(ResourceAllocationDto resourceAllocationDto,long doctorId) {

        List<DoctorsDto>doctorsDtos =  webClient.get()
                .uri("http://localhost:9898/api/v2/user/getAllDoctors")
                .retrieve()
                .bodyToFlux(DoctorsDto.class).collectList().block();

        boolean found=false;
        for(DoctorsDto doctorsDto: doctorsDtos)
        {
            if(doctorsDto.getDoctorId()==doctorId)
            {
                found=true;
                break;
            }
        }
        if(!found)
        {
            throw new InvalidRequestException("Invalid Doctor Id");
        }
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
            if(resourceAllocation1.getDoctorId()==doctorId)
                throw new InvalidRequestException("Resource is already allocated to this doctor");
        }
        resourceAllocation.setRoomNumber(resourceAllocationDto.getRoomNumber());
        resourceAllocationRepo.save(resourceAllocation);
    }
}
