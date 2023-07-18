package com.safetynet.alerts.web.dao;

import com.safetynet.alerts.web.model.Firestation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FirestationDao extends JpaRepository<Firestation, Integer> {

    Firestation findByAddress(String address);

    void deleteByAddress(String address);

    void deleteByStation(String station);
}