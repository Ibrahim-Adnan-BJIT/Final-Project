package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.entity.Slot;

public interface SlotService {

    public SlotDto createSlots(SlotDto slotDto);
    public void cancelSlot(long id);
}
