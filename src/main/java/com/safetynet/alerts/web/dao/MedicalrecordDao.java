package com.safetynet.alerts.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.Medicalrecord;

@Repository
public interface MedicalrecordDao extends JpaRepository<Medicalrecord, Integer>{
    
}
