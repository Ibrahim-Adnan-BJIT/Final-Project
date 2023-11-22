package com.example.appointmentservice.service;

import com.example.appointmentservice.dto.AppointmentDto;

import java.util.List;

public interface AppointmentService {

    public void createAppointment(long slotId);
    public void cancelAppointment(long appointId);

    public List<AppointmentDto> getAppointmentByPatientId();
    public List<AppointmentDto> getAppointmentByDoctorId();
    public List<AppointmentDto>getAllAppointments();
}
