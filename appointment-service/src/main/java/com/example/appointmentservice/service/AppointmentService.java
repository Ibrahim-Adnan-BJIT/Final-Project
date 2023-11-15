package com.example.appointmentservice.service;

public interface AppointmentService {

    public void createAppointment(long slotId);
    public void cancelAppointment(long appointId);
}
