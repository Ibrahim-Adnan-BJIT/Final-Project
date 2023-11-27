package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.SearchDto;
import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.entity.Slot;

import java.util.List;

public interface SlotService {

    public SlotDto createSlots(SlotDto slotDto);
    public void cancelSlot(long id);

    List<Slot> getAllSlotsByDoctorId(long id);

    List<Slot> getMyAllSlots();
    List<SearchDto> getAllSlots();
}
