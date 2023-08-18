package com.safetynet.alerts.web.dao;

import com.safetynet.alerts.web.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Some javadoc.
 * This interface represents the data access object (DAO) for the Firestation
 * entity.
 * It extends JpaRepository to provide standard CRUD operations for Firestation
 * objects.
 */
@Repository
public interface FirestationDao extends JpaRepository<Firestation, Integer> {

    Firestation findByAddress(String address);

    void deleteByAddress(String address);

    void deleteByStation(String station);

    Firestation findByStation(String station);
}