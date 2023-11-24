package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.dto.AppointmentDto;
import com.example.appointmentservice.entity.Appointment;
import com.example.appointmentservice.entity.Notification;
import com.example.appointmentservice.entity.ResourceAllocation;
import com.example.appointmentservice.entity.Slot;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.AppointmentRepo;
import com.example.appointmentservice.repository.NotificationRepo;
import com.example.appointmentservice.repository.ResourceAllocationRepo;
import com.example.appointmentservice.repository.SlotRepo;
import com.example.appointmentservice.service.AppointmentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private SlotRepo slotRepo;

    private AppointmentRepo appointmentRepo;

    private final WebClient webClient;

    private ResourceAllocationRepo resourceAllocationRepo;

    private ModelMapper modelMapper;
    private NotificationRepo notificationRepo;
    private EmailService emailService;


    @Override
    public void createAppointment(long slotId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Optional<Slot> slot=slotRepo.findById(slotId);
        LocalDate currentDate=LocalDate.now();
        if(slot.isEmpty() || slot.get().getStatus().equals("Unavailable") || slot.get().getStatus().equals("Booked"))
        {
            throw new InvalidRequestException("Invalid Slot Id");
        }
        if(slot.get().getDate().isBefore(currentDate))
        {
            throw new InvalidRequestException("Deadline Expired");
        }
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id)
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
        Optional<ResourceAllocation> resourceAllocation= Optional.ofNullable(resourceAllocationRepo.findByDoctorId(slot.get().getDoctorId()));
         if(resourceAllocation.isEmpty())
         {
             throw new InvalidRequestException("Please Wait till Admin Allocate Resource");
         }
        appointment.setRoomNumber(resourceAllocation.get().getRoomNumber());
        appointmentRepo.save(appointment);
        slot.get().setStatus("Booked");
        slotRepo.save(slot.get());

        Notification notification=new Notification();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = slot.get().getDate().format(formatter);
        dateString+=" is your next appointment date, please reach there on time ";
        notification.setMessage(dateString);
        notification.setDoctorStatus(false);
        notification.setPatientStatus(false);
        notification.setPatientId(patientId);
        notification.setDoctorId(slot.get().getDoctorId());
        notificationRepo.save(notification);
        String toPatient=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getEmailForPatient/{id}", patientId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        toPatient.trim();

        String toDoctor=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getEmailForDoctor/{id}", slot.get().getDoctorId())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        toDoctor.trim();



        String patientName=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatientName/{id}", patientId)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String doctorName=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getDoctorName/{id}", slot.get().getDoctorId())
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String topicPatient="Hello "+patientName+" ,Hope you are doing well . Your appointment with "+doctorName+" has been booked successfully .For further queries visit our website .Thank you " +
                "Regards  Admin Expo Health Care";
        String topicDoctor="Hello Doctor "+doctorName+" ,Hope you are doing well . You have an appointment with"+patientName+" In the Upcoming week  .For further queries visit our website .Thank you " +
                "Regards  Admin Expo Health Care";
        String subject="Appointment Notification";

        if (toPatient != null && !toPatient.isEmpty()) {
            emailService.sendEmail(toPatient, subject, topicPatient);
        } else {
           throw new InvalidRequestException("Invalid Email Account");
        }

        if (toDoctor != null && !toDoctor.isEmpty()) {
            emailService.sendEmail(toDoctor, subject, topicDoctor);
        } else {
           throw new InvalidRequestException("Invalid Email Account");
        }

     ;
    }

    @Override
    public void cancelAppointment(long appointId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        Optional<Appointment> appointment=appointmentRepo.findById(appointId);

        if(appointment.isEmpty() || appointment.get().getPatientId()!=patientId)
        {
            throw new InvalidRequestException("Invalid Appointment Id");
        }
        LocalDate currentDate=LocalDate.now();
        if(currentDate.isBefore(appointment.get().getDate())) {
            appointment.get().setStatus("Canceled");
            appointmentRepo.save(appointment.get());
            Slot slot = appointment.get().getSlot();
            slot.setStatus("Available");
            slotRepo.save(slot);
            appointmentRepo.deleteById(appointId);
        }
        else
            throw new InvalidRequestException("Sorry You cant cancel appointment now");


    }

    @Override
    public List<AppointmentDto> getAppointmentByPatientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        List<Appointment>appointments=appointmentRepo.findByPatientId(id1);
       return appointments.stream().map((todo) -> modelMapper.map(todo, AppointmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> getAppointmentByDoctorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 = Long.parseLong(authentication.getName());
        Long doctorId = webClient.get()
                .uri("http://localhost:9898/api/v2/user/getDoctor/" + id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        List<Appointment>appointments=appointmentRepo.findByDoctorId(id1);
        return appointments.stream().map((todo) -> modelMapper.map(todo, AppointmentDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<AppointmentDto> getAllAppointments() {
        List<Appointment>appointments=appointmentRepo.findAll();
        return appointments.stream().map((todo) -> modelMapper.map(todo, AppointmentDto.class))
                .collect(Collectors.toList());
    }


}
