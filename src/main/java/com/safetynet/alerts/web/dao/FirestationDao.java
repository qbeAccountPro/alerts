package com.safetynet.alerts.web.dao;

import com.safetynet.alerts.web.model.Firestations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FirestationDao extends JpaRepository<Firestations, Integer>{
}