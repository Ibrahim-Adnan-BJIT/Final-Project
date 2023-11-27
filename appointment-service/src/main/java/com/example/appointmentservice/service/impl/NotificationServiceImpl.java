package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.entity.Notification;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.NotificationRepo;
import com.example.appointmentservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private NotificationRepo notificationRepo;
 private final WebClient webClient;

    @Override
    public List<Notification> getNotificationsByPatientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        List<Notification>notifications=notificationRepo.findAll();
        List<Notification>notificationList=new ArrayList<>();
        for(Notification notification: notifications)
        {
            if(notification.getPatientId()==patientId && !notification.isPatientStatus())
            {
                notificationList.add(notification);
            }
        }
        return notificationList;
    }

    @Override
    public List<Notification> getNotificationByDoctorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id =  Long.parseLong(authentication.getName());
        Long doctorId = webClient.get()
                .uri("http://localhost:9898/api/v2/user/getDoctor/" + id)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        List<Notification>notifications=notificationRepo.findAll();
        List<Notification>notificationList=new ArrayList<>();
        for(Notification notification: notifications)
        {
            if(notification.getDoctorId()==doctorId && !notification.isDoctorStatus())
            {
                notificationList.add(notification);
            }
        }
        return notificationList;
    }

    @Override
    public void changeDoctorStatus(long notificationId) {
        Notification notification=notificationRepo.findById(notificationId).orElseThrow(()->new InvalidRequestException("Invalid Notification Id"));
        notification.setDoctorStatus(true);
        notificationRepo.save(notification);
    }

    @Override
    public void changePatientStatus(long notificationId) {
        Notification notification=notificationRepo.findById(notificationId).orElseThrow(()->new InvalidRequestException("Invalid Notification Id"));
        notification.setPatientStatus(true);
        notificationRepo.save(notification);
    }
}
