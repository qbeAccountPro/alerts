package com.safetynet.alerts.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

/**
 * Some javadoc.
 * This service class provides operations related to the MedicalRecord entity.
 * It interacts with the MedicalRecordDao to perform CRUD operations on
 * MedicalRecord objects.
 */
@Service
public class MedicalRecordService {
    private BeanService beanService = new BeanService();
    private final MedicalRecordDao medicalRecordDao;

    @Autowired
    public MedicalRecordService(MedicalRecordDao medicalrecordDao) {
        this.medicalRecordDao = medicalrecordDao;
    }

    /**
     * Some javadoc.
     * Retrieves a list of all medical records in the system.
     *
     * @return A list of all MedicalRecord objects.
     */
    public List<MedicalRecord> getAllMedicalRecord() {
        return medicalRecordDao.findAll();
    }

    /**
     * Some javadoc.
     * Retrieves the medical records corresponding to a list of persons.
     *
     * @param persons The list of persons for which to retrieve the medical records.
     * @return A list of MedicalRecord objects corresponding to the provided list of
     *         persons.
     */
    public List<MedicalRecord> getMedicalRecordsByPersons(List<Person> persons, List<MedicalRecord> medicalRecords) {
        List<MedicalRecord> personsMedicalRecords = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (BeanService.normalizeString(person.getFirstName()).equalsIgnoreCase(BeanService
                        .normalizeString(medicalRecord.getFirstName()))
                        && BeanService.normalizeString(person.getLastName()).equalsIgnoreCase(BeanService
                                .normalizeString(medicalRecord.getLastName()))) {
                    personsMedicalRecords.add(medicalRecord);
                    break;
                }
            }
        }
        return personsMedicalRecords;
    }

    /**
     * Some javadoc.
     * Saves a new or existing MedicalRecord object.
     *
     * @param medicalRecord The MedicalRecord object to save.
     */
    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordDao.save(medicalRecord);
    }

    /**
     * Some javadoc.
     * Finds a MedicalRecord object based on the first name and last name.
     *
     * @param firstName The first name of the medical record to find.
     * @param lastName  The last name of the medical record to find.
     * @return The MedicalRecord object corresponding to the provided first name and
     *         last name, or null if not found.
     */
    public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecordDao.findByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Some javadoc.
     * Deletes a MedicalRecord object based on the first name and last name.
     *
     * @param firstName The first name of the medical record to delete.
     * @param lastName  The last name of the medical record to delete.
     */
    public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        medicalRecordDao.deleteByFirstNameAndLastName(firstName, lastName);
    }

    /**
     * Some javadoc.
     * Retrieves the number of minors and adults from a list of medical records.
     *
     * @param medicalRecords The list of medical records for which to count the
     *                       number of minors and adults.
     * @return A list containing the number of minors followed by the number of
     *         adults.
     */
    public List<Integer> getMinorsAndAdultsNumbers(List<MedicalRecord> medicalRecords) {
        int adults = 0, minors = 0;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (isMinor(medicalRecord.getBirthdate())) {
                minors++;
            } else {
                adults++;
            }
        }
        List<Integer> minorsThenAdultsNumbers = new ArrayList<>();
        minorsThenAdultsNumbers.add(minors);
        minorsThenAdultsNumbers.add(adults);
        return minorsThenAdultsNumbers;
    }

    /**
     * Some javadoc.
     * Retrieves a list of medical records corresponding to children from a list of
     * resident medical records.
     *
     * @param residentMedicalRecords The list of resident medical records for which
     *                               to retrieve children medical records.
     * @return A list of MedicalRecord objects corresponding to children from the
     *         provided resident medical records.
     */
    public List<MedicalRecord> getChildrenMedicalRecords(List<MedicalRecord> residentMedicalRecords) {
        List<MedicalRecord> residentChildMedicalRecords = new ArrayList<>();
        for (MedicalRecord residentMedicalRecord : residentMedicalRecords) {
            if (isMinor(residentMedicalRecord.getBirthdate())) {
                residentChildMedicalRecords.add(residentMedicalRecord);
            }
        }
        return residentChildMedicalRecords;
    }

    /**
     * Some javadoc.
     * Checks if a person with the given birthdate is considered a minor (under 18
     * years old).
     *
     * @param birthdate The birthdate of the person in the format "MM/dd/yyyy".
     * @return true if the person is a minor, false otherwise.
     */
    public boolean isMinor(String birthdate) {
        if (beanService.convertBirthdateToAge(birthdate) <= 18) {
            return true;
        } else {
            return false;
        }
    }

}