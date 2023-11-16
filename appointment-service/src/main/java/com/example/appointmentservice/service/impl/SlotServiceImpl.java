package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.entity.Slot;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.SlotRepo;
import com.example.appointmentservice.service.SlotService;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.Lint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {


    private final WebClient webClient;

    private SlotRepo slotRepo;



    @Override
    public SlotDto createSlots(SlotDto slotDto) {
        LocalDate currentDate=LocalDate.now();

        if(slotDto.getDate().equals(currentDate) || slotDto.getDate().isBefore(currentDate))
        {
            throw new InvalidRequestException("Please provide date from Tomorrow and afterwards");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id = Long.parseLong(authentication.getName());
        Long doctorId = webClient.get()
                .uri("http://localhost:9898/api/v2/user/getDoctor/" + id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
       List<Slot>slots=  slotRepo.findByDoctorId(doctorId);
       for(Slot slot: slots)
       {
           if(slotDto.getStartTime().isAfter(slot.getStartTime()) && slotDto.getStartTime().isBefore(slot.getEndTime()))
           {
               throw new InvalidRequestException("Invalid Time Slot");
           }
           else if(slotDto.getStartTime().plusMinutes(30).isAfter(slot.getStartTime()) && slotDto.getStartTime().plusMinutes(30).isBefore(slot.getEndTime()))
           {
               throw new InvalidRequestException("Invalid Time Slot");
           }
       }

        Slot slot = new Slot();
        slot.setDoctorId(doctorId);
        slot.setDate(slotDto.getDate());
        slot.setStartTime(slotDto.getStartTime());
        slot.setEndTime(slotDto.getStartTime().plusMinutes(30)); // Adjusted line
        slot.setStatus("Available");
        slot.setType(slotDto.getType());
        slotRepo.save(slot);

        return slotDto;
    }


    @Override
    public void cancelSlot( long id) {
       Optional<Slot> slot=slotRepo.findById(id);
       if(slot.get().getStatus().equals("Booked"))
       {
           throw new InvalidRequestException("Cant Cancel Booked Appointments");
       }
       slot.get().setStatus("Unavailable");
       slotRepo.save(slot.get());
    }
}
