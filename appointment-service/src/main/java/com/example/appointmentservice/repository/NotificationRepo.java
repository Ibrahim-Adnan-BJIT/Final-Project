package com.example.appointmentservice.repository;

import com.example.appointmentservice.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<Notification,Long> {
}
