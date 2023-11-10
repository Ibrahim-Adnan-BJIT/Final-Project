package com.healthmanagement.doctorservice.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ResourceDto {

    private long allocationId;
    private long doctorId;
    private long roomNumber;
    private List<String> equipments=new ArrayList<>();
}
