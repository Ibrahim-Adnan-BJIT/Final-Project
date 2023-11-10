package com.healthmanagement.doctorservice.repository;

import com.healthmanagement.doctorservice.entity.PatientRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRecordRepo extends JpaRepository<PatientRecord,Long> {
}
