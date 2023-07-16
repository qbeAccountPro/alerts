package com.safetynet.alerts.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.safetynet.alerts.web.model.Person;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {
    Person findById(int id);
    
    /* @Modifying
    @Query("UPDATE Person p SET p.address = :address, p.city = :city, p.zip = :zip, p.phone = :phone, p.email = :email WHERE p.id = :id")
    void updatePerson(@Param("id") int id, @Param("address") String address, @Param("city") String city, @Param("zip") String zip, @Param("phone") String phone, @Param("email") String email);
 */
    void deleteByFirstNameAndLastName(String firstName, String lastName);

    Person findByFirstNameAndLastName(String firstName, String lastName);
}
