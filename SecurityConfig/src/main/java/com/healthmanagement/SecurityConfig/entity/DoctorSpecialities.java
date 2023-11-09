package com.healthmanagement.SecurityConfig.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "doctorspecialities")
@Builder
@Data
@Setter
@Getter
public class DoctorSpecialities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long doctorSpecialityId;
    private long doctorId;

    @ManyToOne
    @JoinColumn(name = "categories_id")
    private Categories categories;
}
