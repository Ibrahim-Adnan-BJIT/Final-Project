package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.entity.ResourceAllocation;
import com.example.appointmentservice.entity.Slot;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.AppointmentRepo;
import com.example.appointmentservice.repository.ResourceAllocationRepo;
import com.example.appointmentservice.repository.SlotRepo;
import com.example.appointmentservice.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private SlotRepo slotRepo;
    private AppointmentRepo appointmentRepo;
    private final WebClient webClient;
    private ResourceAllocationRepo resourceAllocationRepo;
    @Override
    public void createAppointment(long slotId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Optional<Slot> slot=slotRepo.findById(slotId);
        if(!slot.isPresent())
        {
            throw new InvalidRequestException("Invalid Id");
        }
        Long patientId=webClient.get()
                .uri("/api/v2/user/getPatient/{id}", id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Appointment appointment=new Appointment();
        appointment.setDoctorId(slot.get().getDoctorId());
        appointment.setPatientId(patientId);
        appointment.setDate(slot.get().getDate());
        appointment.setLocation("PG Hospital");
        appointment.setSlot(slot.get());
        appointment.setType(slot.get().getType());
        appointment.setStatus("Booked");
        appointment.setStartTime(slot.get().getStartTime());
        appointment.setEndTime(slot.get().getEndTime());
        ResourceAllocation resourceAllocation=resourceAllocationRepo.findByDoctorId(slot.get().getDoctorId());
        appointment.setRoomNumber(resourceAllocation.getRoomNumber());
        appointmentRepo.save(appointment);

    }

    @Override
    public void cancelAppointment(long appointId) {
        Optional<Appointment> appointment=appointmentRepo.findById(appointId);

        if(!appointment.isPresent())
        {
            throw new InvalidRequestException("Invalid Id");
        }
        appointment.get().setStatus("Canceled");
        appointmentRepo.save(appointment.get());
        Slot slot=appointment.get().getSlot();
        slot.setStatus("Available");
        slotRepo.save(slot);


    }
}
