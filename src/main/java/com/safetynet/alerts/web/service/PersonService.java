package com.safetynet.alerts.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * Some javadoc.
 * This service class provides operations related to the Person entity.
 * It interacts with the PersonDao to perform CRUD operations on Person objects.
 */
@Service
public class PersonService {

    @Autowired
    private final PersonDao personDao;

    public PersonService(PersonDao personDao) {
        this.personDao = personDao;
    }

    /**
     * Some javadoc.
     * Retrieves a list of all persons in the system.
     *
     * @return A list of all Person objects.
     */
    public List<Person> getAll() {
        return personDao.findAll();
    }

    /**
     * Some javadoc.
     * Saves a new or existing Person object.
     *
     * @param person The Person object to save.
     * @return The saved Person object.
     */
    public Person savePerson(Person person) {
        return personDao.save(person);
    }

    /**
     * Some javadoc.
     * Retrieves a list of persons corresponding to a list of addresses.
     *
     * @param addresses The list of addresses for which to retrieve the persons.
     * @return A list of Person objects corresponding to the provided list of
     *         addresses.
     */
    public List<Person> getPersonsByAddresses(List<String> addresses) {
        List<Person> persons = getAll();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            for (String address : addresses) {
                if (BeanService.normalizeString(person.getAddress()).equalsIgnoreCase(BeanService
                        .normalizeString(address))) {
                    residents.add(person);
                    break;
                }
            }
        }
        return residents;
    }

    /**
     * Some javadoc.
     * Retrieves a list of persons living at a specific address.
     *
     * @param address The address for which to retrieve the list of persons.
     * @return A list of Person objects living at the provided address.
     */

    public List<Person> getPersonsListByAddress(String address) {
        List<Person> persons = getAll();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            if (BeanService.normalizeString(person.getAddress()).equalsIgnoreCase(BeanService
                    .normalizeString(address))) {
                residents.add(person);
            }
        }
        return residents;
    }

    /**
     * Some javadoc.
     * Retrieves a person based on the provided ID.
     *
     * @param id The ID of the person to find.
     * @return The Person object corresponding to the provided ID, or null if not
     *         found.
     */
    public Person getPersonById(int id) {
        return personDao.findById(id);
    }

    /**
     * Some javadoc.
     * Finds a person based on the first name and last name.
     *
     * @param firstName The first name of the person to find.
     * @param lastName  The last name of the person to find.
     * @return The Person object corresponding to the provided first name and last
     *         name, or null if not found.
     */
    public Person findPersonByFirstNameAndLastName(String firstName, String lastName) {
        return personDao.findByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Some javadoc.
     * Deletes a person based on the provided ID.
     *
     * @param id The ID of the person to delete.
     */
    public void deletePersonById(int id) {
        personDao.deleteById(id);
    }

    /**
     * Some javadoc.
     * Deletes a person based on the provided FirstName and LastName.
     *
     * @param FirstName of the person to delete.
     * @param LastName of the person to delete.
     * 
     */
    public void deletePersonByFirstNameAndLastName(String firstName, String lastName) {
        personDao.deleteByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Some javadoc.
     * Retrieves a list of children from a list of persons and their associated
     * medical records.
     *
     * @param persons        The list of persons for which to retrieve children.
     * @param medicalRecords The list of medical records associated with the
     *                       persons.
     * @return A list of Person objects corresponding to the children from the
     *         provided list of persons and medical records.
     */
    public List<Person> getChildren(List<Person> persons,
            List<MedicalRecord> medicalRecords) {
        MedicalRecordService medicalRecordService = new MedicalRecordService(null);
        List<Person> children = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                Boolean checkFirstName = person.getFirstName().equals(medicalRecord.getFirstName());
                Boolean checkLastName = person.getLastName().equals(medicalRecord.getLastName());
                Boolean checkMinor = medicalRecordService.isMinor(medicalRecord.getBirthdate());
                if (checkFirstName && checkLastName && checkMinor) {
                    children.add(person);
                    break;
                }
            }
        }
        return children;
    }

    /**
     * Some javadoc.
     * Retrieves a list of adults from a list of persons and their associated
     * medical records.
     *
     * @param persons        The list of persons for which to retrieve adults.
     * @param medicalRecords The list of medical records associated with the
     *                       persons.
     * @return A list of Person objects corresponding to the adults from the
     *         provided list of persons and medical records.
     */
    public List<Person> getAdults(List<Person> persons, List<MedicalRecord> medicalRecords) {
        MedicalRecordService medicalRecordService = new MedicalRecordService(null);
        List<Person> adults = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                Boolean checkFirstName = person.getFirstName().equals(medicalRecord.getFirstName());
                Boolean checkLastName = person.getLastName().equals(medicalRecord.getLastName());
                Boolean checkMinor = medicalRecordService.isMinor(medicalRecord.getBirthdate());
                if (checkFirstName && checkLastName && !checkMinor) {
                    adults.add(person);
                    break;
                }
            }
        }
        return adults;
    }

    /**
     * Some javadoc.
     * Retrieves a list of persons living in a specific city.
     *
     * @param city The city for which to retrieve the list of persons.
     * @return A list of Person objects living in the provided city.
     */
    public List<Person> getPersonsByCity(String city) {
        List<Person> persons = getAll();
        List<Person> residents = new ArrayList<>();
        for (Person person : persons) {
            Boolean checkCity = person.getCity().equals(city);
            if (checkCity) {
                residents.add(person);
            }
        }
        return residents;
    }

    /**
     * Some javadoc.
     * Retrieves a list of persons based on the provided first name and last name.
     *
     * @param firstName The first name of the persons to find.
     * @param lastName  The last name of the persons to find.
     * @return A list of Person objects corresponding to the provided first name and
     *         last name.
     */
    public List<Person> getPersonsByFirstNameAndLastName(String firstName, String lastName) {
        List<Person> persons = getAll();
        List<Person> matchingPersons = new ArrayList<>();
        for (Person person : persons) {
            Boolean checkFirstName = person.getFirstName().equals(firstName);
            Boolean checkLastName = person.getLastName().equals(lastName);
            if (checkFirstName && checkLastName) {
                matchingPersons.add(person);
            }
        }
        return matchingPersons;
    }
}