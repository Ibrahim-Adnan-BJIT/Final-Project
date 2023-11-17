package com.example.ClinicalDecisionSupportSystemService.service;

import com.example.ClinicalDecisionSupportSystemService.dto.ProgressDto;

import java.util.List;

public interface ProgressService {

    void createProgress(ProgressDto progressDto);

    void updateProgress(ProgressDto progressDto,long id);

    List<ProgressDto> completedProgressesByPatientId();

    List<ProgressDto> incompleteProgressesByPatientId();

    void deleteProgress(long id);

}
