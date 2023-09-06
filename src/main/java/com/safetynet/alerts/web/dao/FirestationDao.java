package com.safetynet.alerts.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.Firestation;

/**
 * Some javadoc.
 * 
 * This interface represents the data access object (DAO) for the Firestation
 * entity.
 * 
 * It extends JpaRepository to provide standard CRUD operations for Firestation
 * objects.
 */
@Repository
public interface FirestationDao extends JpaRepository<Firestation, Integer> {

    List<Firestation> findAll();

    void deleteByStation(String station);

    Firestation findById(int id);

    Firestation findByStation(String station);
}