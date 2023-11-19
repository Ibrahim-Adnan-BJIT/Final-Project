package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.entity.Recommendation;
import com.example.ClinicalDecisionSupportSystemService.repository.RecommendationRepo;
import com.example.ClinicalDecisionSupportSystemService.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class RecommendationServiceImpl implements RecommendationService {
    private final WebClient webClient;

    private RecommendationRepo recommendationRepo;
    @Override
    public Recommendation getRecommendationByPatientId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();

        return recommendationRepo.findByPatientId(patientId);
    }
}
