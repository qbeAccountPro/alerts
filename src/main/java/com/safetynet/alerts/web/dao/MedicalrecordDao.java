package com.safetynet.alerts.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.MedicalRecord;

@Repository
public interface MedicalRecordDao extends JpaRepository<MedicalRecord, Integer>{

    MedicalRecord findByFirstNameAndLastName(String firstName, String lastName);

    void deleteByFirstNameAndLastName(String firstName, String lastName);
    
}
