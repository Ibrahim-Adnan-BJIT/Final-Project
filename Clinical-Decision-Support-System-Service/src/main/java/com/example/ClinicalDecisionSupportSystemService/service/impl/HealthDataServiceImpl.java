package com.example.ClinicalDecisionSupportSystemService.service.impl;

import com.example.ClinicalDecisionSupportSystemService.dto.HealthDataDto;
import com.example.ClinicalDecisionSupportSystemService.entity.*;
import com.example.ClinicalDecisionSupportSystemService.exception.InvalidRequestException;
import com.example.ClinicalDecisionSupportSystemService.repository.*;
import com.example.ClinicalDecisionSupportSystemService.service.HealthDataService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class HealthDataServiceImpl implements HealthDataService {
    private HealthDataRepo healthDataRepo;
    private final WebClient webClient;
    private RecommendationRepo recommendationRepo;
    private BmiRecommendationRepo bmiRecommendationRepo;
    private BloodPressureRecommendationRepo bloodPressureRecommendationRepo;
    private DiabetesRecommendationRepo diabetesRecommendationRepo;
    private AllergyRecommendationRepo allergyRecommendationRepo;
    private PreviousHealthDataRepo previousHealthDataRepo;
    @Override
    public void storeHealthData(HealthDataDto healthDataDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
     Optional<HealthData> healthData= Optional.ofNullable(healthDataRepo.findByPatientId(patientId));

        if(healthData.isEmpty())
        {
            HealthData healthData1=new HealthData();
            if(healthDataDto.getWeight()<0 || healthDataDto.getHeight()<0)
            {
                throw new InvalidRequestException("Enter valid number");
            }

            healthData1.setPatientId(patientId);
            healthData1.setWeight(healthDataDto.getWeight());
            healthData1.setHeight(healthDataDto.getHeight());
            double bmi= (double) (healthDataDto.getWeight()) /(healthDataDto.getHeight()* healthDataDto.getHeight());
          healthData1.setBmi(bmi);
          healthData1.setAllergy(healthDataDto.getAllergy());
          healthData1.setDiabetes(healthDataDto.getDiabetes());
          healthData1.setBloodPressure(healthData1.getBloodPressure());
          healthDataRepo.save(healthData1);

            int cnt=0; // for prediction lol..

            Recommendation recommendation=new Recommendation();
            recommendation.setPatientId(patientId);


            List<BmiRecommendation> bmiRecommendationList=bmiRecommendationRepo.findAll();
            for(BmiRecommendation bmiRecommendation: bmiRecommendationList)
            {
                if(bmi<18.5)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getUnderweight());

                }
                else if(bmi>=18.5 && bmi<=24.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getNormalWeight());
                    ++cnt;
                }
                else if(bmi>=25 && bmi<=29.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getOverweight());

                }
                else if(bmi>=30 && bmi <=34.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassI());
                    --cnt;
                }
                else if(bmi>=35 && bmi<=39.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassII());
                    cnt-=2;
                }
                else
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassIII());
                    cnt-=3;
                }
            }

            List<BloodPressureRecommendation> bloodPressureRecommendations = bloodPressureRecommendationRepo.findAll();
            BloodPressure bloodPressure = healthData1.getBloodPressure();

            for (BloodPressureRecommendation bloodPressureRecommendation : bloodPressureRecommendations) {
                if (bloodPressure == BloodPressure.NORMAL) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getNormal());
                    ++cnt;
                } else if (bloodPressure == BloodPressure.ELEVATED) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getElevated());

                } else if (bloodPressure == BloodPressure.HYPERTENSION_STAGE_1) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertension_stage_1());
                    --cnt;
                } else if (bloodPressure == BloodPressure.HYPERTENSION_STAGE_2) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertension_stage_2());
                    cnt-=2;
                } else
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertensive_crisis());
                cnt-=3;
                }
                // Handle the default case if needed




            List<AllergyRecommendation> allergies = allergyRecommendationRepo.findAll();
                for (AllergyRecommendation allergyRecommendation : allergies) {
                    Allergy allergy = healthData1.getAllergy();

                    if (allergy == Allergy.GLUTEN) {
                        recommendation.setAllergyRecom(allergyRecommendation.getGluten());

                    } else if (allergy == Allergy.PEANUT) {
                        recommendation.setAllergyRecom(allergyRecommendation.getPeanut());

                    } else if (allergy == Allergy.LACTOSE) {
                        recommendation.setAllergyRecom(allergyRecommendation.getLactose());

                    } else if (allergy == Allergy.SHELLFISH) {
                        recommendation.setAllergyRecom(allergyRecommendation.getShellfish());

                    } else if (allergy == Allergy.TREE_NUT) {
                        recommendation.setAllergyRecom(allergyRecommendation.getTree_nut());

                    } else if (allergy == Allergy.EGG) {
                        recommendation.setAllergyRecom(allergyRecommendation.getEgg());

                    } else if (allergy == Allergy.SOY) {
                        recommendation.setAllergyRecom(allergyRecommendation.getSoy());

                    }
                }

            List<DiabetesRecommendation> diabetesRecommendations = diabetesRecommendationRepo.findAll();
            for (DiabetesRecommendation diabetesRecommendation : diabetesRecommendations) {
                Diabetes diabetesType = healthData1.getDiabetes();

                if (diabetesType == Diabetes.NORMAL) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getNormal());
                    ++cnt;
                } else if (diabetesType == Diabetes.TYPE_1) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getType1());
                    --cnt;
                } else if (diabetesType == Diabetes.TYPE_2) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getType2());
                    cnt-=2;
                }
            }
            PreviousHealthData previousHealthData=new PreviousHealthData();
            if(cnt<0 && cnt>=-2)
            {
                previousHealthData.setState(State.POOR);
            }
            else if(cnt<-2 && cnt>=-4)
                previousHealthData.setState(State.VERY_POOR);
            else if(cnt<-4)
                previousHealthData.setState(State.CONSULT_A_DOCTOR);
            else if(cnt>=0 && cnt<2)
                previousHealthData.setState(State.NORMAL);
            else if(cnt>=2 && cnt<4)
                previousHealthData.setState(State.GOOD);
            else
                previousHealthData.setState(State.VERY_GOOD);

            previousHealthData.setDate(LocalDate.now());

           previousHealthDataRepo.save(previousHealthData);

          recommendationRepo.save(recommendation);
        }
        else
        {
            int cnt=0; // for prediction lol..

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

          Optional <Recommendation> recommendation1= Optional.ofNullable(recommendationRepo.findByPatientId(patientId));
            if(recommendation1.isEmpty())
            {
                throw new InvalidRequestException("Invalid Request");
            }
            Recommendation recommendation=recommendation1.get();
            List<BmiRecommendation> bmiRecommendationList=bmiRecommendationRepo.findAll();
            for(BmiRecommendation bmiRecommendation: bmiRecommendationList)
            {
                if(bmi<18.5)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getUnderweight());

                }
                else if(bmi>=18.5 && bmi<=24.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getNormalWeight());
                    ++cnt;
                }
                else if(bmi>=25 && bmi<=29.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getOverweight());
                }
                else if(bmi>=30 && bmi <=34.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassI());
                    cnt--;
                }
                else if(bmi>=35 && bmi<=39.9)
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassII());
                    cnt-=2;
                }
                else
                {
                    recommendation.setBmiRecom(bmiRecommendation.getObesityClassIII());
                    cnt-=3;
                }
            }

            List<BloodPressureRecommendation> bloodPressureRecommendations = bloodPressureRecommendationRepo.findAll();
            BloodPressure bloodPressure = healthData.get().getBloodPressure();

            for (BloodPressureRecommendation bloodPressureRecommendation : bloodPressureRecommendations) {
                if (bloodPressure == BloodPressure.NORMAL) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getNormal());
                    cnt++;
                } else if (bloodPressure == BloodPressure.ELEVATED) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getElevated());
                } else if (bloodPressure == BloodPressure.HYPERTENSION_STAGE_1) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertension_stage_1());
                    cnt-=1;
                } else if (bloodPressure == BloodPressure.HYPERTENSION_STAGE_2) {
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertension_stage_2());
                    cnt-=2;
                } else
                    recommendation.setBloodPressureRecom(bloodPressureRecommendation.getHypertensive_crisis());
                cnt-=3;
            }

            List<AllergyRecommendation> allergies = allergyRecommendationRepo.findAll();
            for (AllergyRecommendation allergyRecommendation : allergies) {
                Allergy allergy = healthData.get().getAllergy();

                if (allergy == Allergy.GLUTEN) {
                    recommendation.setAllergyRecom(allergyRecommendation.getGluten());
                } else if (allergy == Allergy.PEANUT) {
                    recommendation.setAllergyRecom(allergyRecommendation.getPeanut());
                } else if (allergy == Allergy.LACTOSE) {
                    recommendation.setAllergyRecom(allergyRecommendation.getLactose());
                } else if (allergy == Allergy.SHELLFISH) {
                    recommendation.setAllergyRecom(allergyRecommendation.getShellfish());
                } else if (allergy == Allergy.TREE_NUT) {
                    recommendation.setAllergyRecom(allergyRecommendation.getTree_nut());
                } else if (allergy == Allergy.EGG) {
                    recommendation.setAllergyRecom(allergyRecommendation.getEgg());
                } else if (allergy == Allergy.SOY) {
                    recommendation.setAllergyRecom(allergyRecommendation.getSoy());
                }
            }

            List<DiabetesRecommendation> diabetesRecommendations = diabetesRecommendationRepo.findAll();
            for (DiabetesRecommendation diabetesRecommendation : diabetesRecommendations) {
                Diabetes diabetesType = healthData.get().getDiabetes();

                if (diabetesType == Diabetes.NORMAL) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getNormal());
                    ++cnt;
                } else if (diabetesType == Diabetes.TYPE_1) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getType1());
                    cnt-=1;
                } else if (diabetesType == Diabetes.TYPE_2) {
                    recommendation.setDiabetesRecom(diabetesRecommendation.getType2());
                    cnt-=2;
                }
            }
            PreviousHealthData previousHealthData=new PreviousHealthData();
            if(cnt<0 && cnt>=-2)
            {
                previousHealthData.setState(State.POOR);
            }
            else if(cnt<-2 && cnt>=-4)
                previousHealthData.setState(State.VERY_POOR);
            else if(cnt<-4)
                previousHealthData.setState(State.CONSULT_A_DOCTOR);
            else if(cnt>=0 && cnt<2)
                previousHealthData.setState(State.NORMAL);
            else if(cnt>=2 && cnt<4)
                previousHealthData.setState(State.GOOD);
            else
                previousHealthData.setState(State.VERY_GOOD);

            previousHealthData.setDate(LocalDate.now());
            previousHealthDataRepo.save(previousHealthData);
            recommendationRepo.save(recommendation);

        }
    }

    @Override
    public Optional<HealthData> getHealthData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        long id1 =  Long.parseLong(authentication.getName());
        Long patientId=webClient.get()
                .uri("http://localhost:9898/api/v2/user/getPatient/{id}", id1)
                .retrieve()
                .bodyToMono(Long.class)
                .block();
        HealthData healthData=healthDataRepo.findByPatientId(patientId);
        return Optional.ofNullable(healthData);
    }

    @Override
    public List<PreviousHealthData> getAllHealthTrack() {
        return previousHealthDataRepo.findAll();
    }
}
