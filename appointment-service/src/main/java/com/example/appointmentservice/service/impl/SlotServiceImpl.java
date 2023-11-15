package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.entity.Slot;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.SlotRepo;
import com.example.appointmentservice.service.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SlotServiceImpl implements SlotService {

    private final WebClient webClient;
    private SlotRepo slotRepo;



    @Override
    public SlotDto createSlots(SlotDto slotDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Long doctorId=webClient.get()
                .uri("/api/v2/user/getDoctor/{id}", id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Slot slot=new Slot();
        slot.setDoctorId(doctorId);
        slot.setDate(slotDto.getDate());
        slot.setStartTime(slotDto.getStartTime());
        slot.setEndTime(LocalTime.from(LocalDate.from(slotDto.getStartTime().plusMinutes(30))));
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
