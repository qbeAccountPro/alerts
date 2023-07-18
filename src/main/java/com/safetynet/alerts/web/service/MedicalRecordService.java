package com.safetynet.alerts.web.service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

@Service
public class MedicalRecordService {

    private final MedicalRecordDao medicalRecordDao;

    @Autowired
    public MedicalRecordService(MedicalRecordDao medicalrecordDao) {
        this.medicalRecordDao = medicalrecordDao;
    }

    public List<MedicalRecord> getAllMedicalRecord() {
        return medicalRecordDao.findAll();
    }

    public List<MedicalRecord> getMedicalRecordsByPersons(List<Person> persons) {
        List<MedicalRecord> allMedicalRecords = getAllMedicalRecord();
        List<MedicalRecord> personsMedicalRecords = new ArrayList<>();
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : allMedicalRecords) {
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

    public void saveMedicalRecord(MedicalRecord medicalRecord) {
        medicalRecordDao.save(medicalRecord);
    }

    public MedicalRecord findMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        return medicalRecordDao.findByFirstNameAndLastName(firstName, lastName);
    }

    public void deleteMedicalRecordByFirstNameAndLastName(String firstName, String lastName) {
        medicalRecordDao.deleteByFirstNameAndLastName(firstName, lastName);
    }

    public String getMinorsAndAdultsNumbers(List<MedicalRecord> medicalRecords) {
        int adults = 0, minors = 0;
        for (MedicalRecord medicalRecord : medicalRecords) {
            if (isMinor(medicalRecord.getBirthdate())) {
                minors++;
            } else {
                adults++;
            }
        }
        return "There are " + adults + " adults and " + minors + " minors.";
    }

    public List<MedicalRecord> getMedicalRecordsOnlyFromChild(List<MedicalRecord> residentMedicalRecords) {
        List<MedicalRecord> residentChildMedicalRecords = new ArrayList<>();
        for (MedicalRecord residentMedicalRecord : residentMedicalRecords) {
            if (isMinor(residentMedicalRecord.getBirthdate())) {
                residentChildMedicalRecords.add(residentMedicalRecord);
            }
        }
        return residentChildMedicalRecords;
    }

    private boolean isMinor(String birthdate) {
        if (convertBirthdateToAge(birthdate) <= 18) {
            return true;
        } else {
            return false;
        }
    }

    public int convertBirthdateToAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }
}