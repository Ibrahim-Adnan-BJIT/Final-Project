package com.example.ClinicalDecisionSupportSystemService.service.impl;


import com.example.ClinicalDecisionSupportSystemService.dto.ProgressDto;
import com.example.ClinicalDecisionSupportSystemService.entity.Progress;
import com.example.ClinicalDecisionSupportSystemService.exception.InvalidRequestException;
import com.example.ClinicalDecisionSupportSystemService.repository.ProgressRepo;
import com.example.ClinicalDecisionSupportSystemService.service.ProgressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    private ProgressRepo progressRepo;
    private final WebClient webClient;
    private ModelMapper modelMapper;

    @Override
    public void createProgress(ProgressDto progressDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        if(progressDto.getProgression()<0 || progressDto.getProgression()>100)
        {
            throw new InvalidRequestException("Invalid Progress report");
        }
        Progress progress=new Progress();
        progress.setPatientId(patientId);
        progress.setProgression(progressDto.getProgression());
        progress.setDescription(progressDto.getDescription());
        progressRepo.save(progress);
    }

    @Override
    public void updateProgress(ProgressDto progressDto, long id) {
        // Check if progress with the given goalId exists
        if (!progressRepo.existsById(id)) {
            throw new InvalidRequestException("Invalid Goal Id");
        }

        Optional<Progress> optionalProgress = progressRepo.findById(id);

        if (optionalProgress.isPresent()) {
            Progress progress = optionalProgress.get();
            progress.setProgression(progressDto.getProgression());
            progress.setDescription(progressDto.getDescription());
            progressRepo.save(progress);
        } else {
            // Handle the case where progress with the given ID is not found
            throw new EntityNotFoundException("Progress not found for ID: " + id);
        }
    }



    @Override
    public List<ProgressDto> completedProgressesByPatientId() {
       List<Progress>progresses=progressRepo.findAll();
       List<ProgressDto>progressDtos=new ArrayList<>();
       for(Progress progress: progresses)
       {
           if(progress.getProgression()==100)
           {
               progressDtos.add(modelMapper.map(progress,ProgressDto.class));
           }
       }
       return progressDtos;
    }

    @Override
    public List<ProgressDto> incompleteProgressesByPatientId() {
        List<Progress>progresses=progressRepo.findAll();
        List<ProgressDto>progressDtos=new ArrayList<>();
        for(Progress progress: progresses)
        {
            if(progress.getProgression()!=100)
            {
                progressDtos.add(modelMapper.map(progress,ProgressDto.class));
            }
        }
        return progressDtos;
    }

    @Override
    public void deleteProgress(long id) {
        List<Progress>progresses=progressRepo.findAll();
        boolean found=false;
        for(Progress progress: progresses)
        {
            if(progress.getGoalId()==id)
            {
                found=true;
                break;
            }
        }
        if(!found)
        {
            throw new InvalidRequestException("Invalid Goal Id");
        }
        Optional<Progress> progress=progressRepo.findById(id);
        if(progress.get().getProgression()!=100)
        {
            throw new InvalidRequestException("Please Complete your Progress before deleting it");
        }
        progressRepo.deleteById(id);
    }
}
