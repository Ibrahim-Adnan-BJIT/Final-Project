package com.example.appointmentservice.service.impl;

import com.example.appointmentservice.entity.Medicine;
import com.example.appointmentservice.exception.InvalidRequestException;
import com.example.appointmentservice.repository.MedicineRepo;
import com.example.appointmentservice.service.MedicineService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MedicineServiceImpl implements MedicineService {
    private MedicineRepo medicineRepo;
    @Override
    public void createMedicine(Medicine medicine) {
        medicineRepo.save(medicine);
    }

    @Override
    public void updateMedicine(Medicine medicine, long id) {
          Medicine medicine1=medicineRepo.findById(id).orElseThrow(()->new InvalidRequestException("Invalid Medicine Id"));

          medicine1.setMedicineName(medicine.getMedicineName());
          medicine1.setDescription(medicine.getDescription());
          medicine1.setExpire(medicine.getExpire());
          medicineRepo.save(medicine1);
    }

    @Override
    public List<Medicine> getAllMedicine() {
      List<Medicine>medicines=medicineRepo.findAll();
      List<Medicine>medicineList=new ArrayList<>();
      for(Medicine medicine: medicines)
      {
          if(!medicine.getExpire().isBefore(LocalDate.now()))
          {
              medicineList.add(medicine);
          }
      }
      return medicineList;
    }

    @Override
    public Medicine getMedicineById(long id) {
        Medicine medicine1=medicineRepo.findById(id).orElseThrow(()->new InvalidRequestException("Invalid Medicine Id"));
        return medicine1;
    }

    @Override
    public void deleteMedicine(long id) {
        Medicine medicine1=medicineRepo.findById(id).orElseThrow(()->new InvalidRequestException("Invalid Medicine Id"));
        medicineRepo.deleteById(id);
    }
}
