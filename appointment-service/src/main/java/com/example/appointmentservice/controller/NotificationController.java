package com.example.appointmentservice.controller;

import com.example.appointmentservice.entity.Notification;
import com.example.appointmentservice.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.PrimitiveIterator;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v2")
public class NotificationController {

    private NotificationService notificationService;

    @GetMapping("/getNotificationForPatient")
    public ResponseEntity<List<Notification>>getNotificationForPatient()
    {
        List<Notification>notifications=notificationService.getNotificationsByPatientId();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    @GetMapping("/getNotificationForDoctor")
    public ResponseEntity<List<Notification>>getNotificationForDoctor()
    {
        List<Notification>notifications=notificationService.getNotificationByDoctorId();
        return new ResponseEntity<>(notifications,HttpStatus.OK);
    }
    @PutMapping("/changeDoctorStatus/{notificationId}")
    public ResponseEntity<String> changeDoctorView(@PathVariable long notificationId)
    {
        notificationService.changeDoctorStatus(notificationId);
        return new ResponseEntity<>("Status changed",HttpStatus.OK);
    }
    @PutMapping("/changePatientStatus/{notificationId}")
    public ResponseEntity<String> changePatientView(@PathVariable long notificationId)
    {
        notificationService.changePatientStatus(notificationId);
        return new ResponseEntity<>("Status changed",HttpStatus.OK);
    }
}
