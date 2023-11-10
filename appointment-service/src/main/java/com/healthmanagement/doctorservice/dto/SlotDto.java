package com.healthmanagement.doctorservice.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SlotDto {

    private LocalDate appointDate;
    private long slot;
    private long capacity;
}
