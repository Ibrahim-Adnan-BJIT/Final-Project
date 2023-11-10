package com.healthmanagement.doctorservice.service.impl;

import com.healthmanagement.doctorservice.dto.SlotDto;
import com.healthmanagement.doctorservice.entity.InPersonSlot;
import com.healthmanagement.doctorservice.feignClient.SecurityServiceClient;
import com.healthmanagement.doctorservice.repository.InPersonSlotRepo;
import com.healthmanagement.doctorservice.service.InPersonSlotService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InPersonSlotServiceImpl implements InPersonSlotService {

    private InPersonSlotRepo inPersonSlotRepo;
    private SecurityServiceClient securityServiceClient;

    @Override
    public void createSlots(SlotDto slotDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        long doctorId=securityServiceClient.getDoctor(id);
        InPersonSlot inPersonSlot=new InPersonSlot();
        inPersonSlot.setDoctorId(doctorId);
        inPersonSlot.setSlot(slotDto.getSlot());
        inPersonSlot.setCapacity(20);
        inPersonSlot.setAppointDate(slotDto.getAppointDate());
        inPersonSlotRepo.save(inPersonSlot);
    }
}
