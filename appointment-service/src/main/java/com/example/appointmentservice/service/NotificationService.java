package com.example.appointmentservice.service;

import com.example.appointmentservice.entity.Notification;

import java.util.List;

public interface NotificationService {

         List<Notification> getNotificationsByPatientId();

         List<Notification>getNotificationByDoctorId();

         void changeDoctorStatus(long notificationId);
          void changePatientStatus(long notificationId);
}
