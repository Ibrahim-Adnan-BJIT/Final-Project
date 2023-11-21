package com.example.appointmentservice.service;

import com.example.appointmentservice.entity.Medicine;
import jdk.dynalink.linker.LinkerServices;

import java.util.List;

public interface MedicineService {

    void createMedicine(Medicine medicine);
    void updateMedicine(Medicine medicine,long id);

    List<Medicine> getAllMedicine();

    Medicine getMedicineById(long id);

    void deleteMedicine(long id);

}
