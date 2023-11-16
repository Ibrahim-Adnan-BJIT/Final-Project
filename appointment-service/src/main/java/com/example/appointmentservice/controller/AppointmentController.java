package com.example.appointmentservice.controller;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.dto.ResourceAllocationDto;
import com.example.appointmentservice.dto.SlotDto;
import com.example.appointmentservice.service.AppointmentService;
import com.example.appointmentservice.service.ResourceAllocationService;
import com.example.appointmentservice.service.SlotService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
@AllArgsConstructor
public class AppointmentController {

    @Autowired
    private SlotService slotService;
    @Autowired
    private ResourceAllocationService resourceAllocationService;
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create/slots")
    public ResponseEntity<SlotDto> createSlots(@RequestBody SlotDto slotDto)
    {
        SlotDto slotDto1=slotService.createSlots(slotDto);
        return new ResponseEntity<>(slotDto1, HttpStatus.CREATED);
    }
    @PostMapping("/cancel/slots/{id}")
    public ResponseEntity<String> cancelSlots(@PathVariable long id)
    {
        slotService.cancelSlot(id);
        return new ResponseEntity<>("Slot is canceled",HttpStatus.ACCEPTED);
    }

    @PostMapping("/create/resource/{id}")
    public ResponseEntity<String> createResource(@RequestBody ResourceAllocationDto resourceAllocationDto,@PathVariable long id)
    {
        resourceAllocationService.createResource(resourceAllocationDto,id);
        return new ResponseEntity<>("Resource Allocated Successfully",HttpStatus.CREATED);
    }

    @PostMapping("/create/appointment/{id}")
    public ResponseEntity<String> createAppointment(@PathVariable long id)
    {
        appointmentService.createAppointment(id);
        return new ResponseEntity<>("Appointment created Successfully",HttpStatus.CREATED);
    }

    @PostMapping("/cancel/appointment/{id}")
    public ResponseEntity<String> cancelAppointment(@PathVariable long id)
    {
        appointmentService.cancelAppointment(id);
        return new ResponseEntity<>("Appointment cancelled successfully",HttpStatus.ACCEPTED);
    }

    @GetMapping("/getAllAppointmentByPatientId/{id}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByPatientId(@PathVariable long id)
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAppointmentByPatientId(id);
        return new ResponseEntity<>(appointmentDtos,HttpStatus.FOUND);
    }

    @GetMapping("/getAllAppointmentByDoctorId/{id}")
    public ResponseEntity<List<AppointmentDto>> getAllAppointmentByDoctorId(@PathVariable long id)
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAppointmentByDoctorId(id);
        return new ResponseEntity<>(appointmentDtos,HttpStatus.FOUND);
    }

    @GetMapping("/getAllAppointments")
    public ResponseEntity<List<AppointmentDto>> getAllAppointments()
    {
        List<AppointmentDto>appointmentDtos=appointmentService.getAllAppointments();
        return new ResponseEntity<>(appointmentDtos,HttpStatus.FOUND);
    }
}
