package com.safetynet.alerts.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.MedicalRecord;

/**
 * Some javadoc.
 * 
 * This interface represents the data access object (DAO) for the MedicalRecord
 * entity.
 * 
 * It extends JpaRepository to provide standard CRUD operations for
 * MedicalRecord objects.
 */
@Repository
public interface MedicalRecordDao extends JpaRepository<MedicalRecord, Integer> {

  MedicalRecord findByIdPerson(int idPerson);

  void deleteByIdPerson(Integer idPerson);
}
