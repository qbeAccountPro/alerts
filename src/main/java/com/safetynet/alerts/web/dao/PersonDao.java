package com.safetynet.alerts.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.Person;

/**
 * Some javadoc.
 * This interface represents the data access object (DAO) for the Person entity.
 * It extends JpaRepository to provide standard CRUD operations for Person
 * objects.
 */
@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {
    Person findById(int id);

    void deleteByFirstNameAndLastName(String firstName, String lastName);

    Person findByFirstNameAndLastName(String firstName, String lastName);
}
