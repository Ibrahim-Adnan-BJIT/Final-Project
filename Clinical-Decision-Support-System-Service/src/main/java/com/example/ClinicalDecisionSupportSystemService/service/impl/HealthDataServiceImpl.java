package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.entity.HealthData;
import com.example.ClinicalDecisionSupportSystemService.exception.InvalidRequestException;
import com.example.ClinicalDecisionSupportSystemService.repository.HealthDataRepo;
import com.example.ClinicalDecisionSupportSystemService.service.HealthDataService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
@AllArgsConstructor
public class HealthDataServiceImpl implements HealthDataService {
    private HealthDataRepo healthDataRepo;
    private final WebClient webClient;
    @Override
    public void storeHealthData(HealthDataDto healthDataDto) {
        Optional<HealthData> healthData=healthDataRepo.findById(1L);
        if(healthData.isEmpty())
        {
            HealthData healthData1=new HealthData();
            if(healthDataDto.getWeight()<0 || healthDataDto.getHeight()<0)
            {
                throw new InvalidRequestException("Enter valid number");
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            long id1 =  Long.parseLong(authentication.getName());
            Long patientId=webClient.get()
                    .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                    .retrieve()
                    .bodyToMono(Long.class)
                    .block();
            healthData1.setPatientId(patientId);
            healthData1.setWeight(healthDataDto.getWeight());
            healthData1.setHeight(healthDataDto.getHeight());
            double bmi= (double) (healthDataDto.getWeight()) /(healthDataDto.getHeight()* healthDataDto.getHeight());
          healthData1.setBmi(bmi);
          healthData1.setAllergy(healthDataDto.getAllergy());
          healthData1.setDiabetes(healthDataDto.getDiabetes());
          healthData1.setBloodPressure(healthData1.getBloodPressure());
          healthDataRepo.save(healthData1);
        }
        else
        {
            if(healthDataDto.getWeight()<0 || healthDataDto.getHeight()<0 )
            {
                throw new InvalidRequestException("Enter valid number");
            }

             healthData.get().setWeight(healthDataDto.getWeight());
             healthData.get().setHeight(healthDataDto.getHeight());
            double bmi= (double) (healthDataDto.getWeight()) /(healthDataDto.getHeight()* healthDataDto.getHeight());
            healthData.get().setBmi(bmi);
            healthData.get().setBloodPressure(healthDataDto.getBloodPressure());
            healthData.get().setAllergy(healthDataDto.getAllergy());
            healthData.get().setDiabetes(healthDataDto.getDiabetes());
            healthDataRepo.save(healthData.get());
        }
    }
}
