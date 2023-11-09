package com.healthmanagement.SecurityConfig.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "admins")
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Setter
@Getter
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adminId;
    private long userId;

}
