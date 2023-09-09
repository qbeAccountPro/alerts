package com.safetynet.alerts.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.Household;

/**
 * Some javadoc.
 * 
 * This interface represents the data access object (DAO) for the Household
 * object.
 * 
 * It extends JpaRepository to provide standard CRUD operations for Firestation
 * objects.
 */
@Repository
public interface HouseholdDao extends JpaRepository<Household, Integer> {
  List<Household> findAll();

  Household findByAddress(String address);
}
