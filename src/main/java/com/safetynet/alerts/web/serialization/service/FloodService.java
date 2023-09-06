package com.safetynet.alerts.web.serialization.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.FloodAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;
import com.safetynet.alerts.web.service.BeanService;

/**
 * Some javadoc.
 * 
 * Service class for generating a list of Flood objects from a list of Person
 * and MedicalRecord objects.
 */
@Service
public class FloodService {
    BeanService beanService = new BeanService();

    /**
     * Some javadoc.
     * 
     * Generate a list of Flood objects from a list of Person and MedicalRecord
     * objects.
     *
     * @param persons        The list of Person objects.
     * @param medicalRecords The list of MedicalRecord objects.
     * @return A list of Flood objects representing persons affected by flood from
     *         the input data.
     */
    public List<FloodAlertByHousehold> getFloodAlertByHousehold(List<Person> persons,
            List<MedicalRecord> medicalRecords,
            List<Household> households) {
        List<FloodAlertByHousehold> floodsAlertByHousehold = new ArrayList<>();
        // Get all households into floodAlertByHousehold :
        for (Household household : households) {
            FloodAlertByHousehold floodAlertByHousehold = new FloodAlertByHousehold();
            floodAlertByHousehold.setHousehold(household);
            floodsAlertByHousehold.add(floodAlertByHousehold);
        }
        for (Person person : persons) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                if (person.getId() == medicalRecord.getIdPerson()) {
                    FloodAlert flood = new FloodAlert();
                    flood.setLastName(person.getLastName());
                    flood.setPhone(person.getPhone());
                    flood.setAge(beanService.convertBirthdateToAge(medicalRecord.getBirthdate()));
                    flood.setMedications(medicalRecord.getMedications());
                    flood.setAllergies(medicalRecord.getAllergies());
                    for (FloodAlertByHousehold floodAlertByHousehold : floodsAlertByHousehold) {
                        if (floodAlertByHousehold.getHousehold().getId() == person.getIdHousehold()) {
                            if (floodAlertByHousehold.getFloods() == null) {
                                List<FloodAlert> floods = new ArrayList<>();
                                floods.add(flood);
                                floodAlertByHousehold.setFloods(floods);
                                break;
                            } else {
                                floodAlertByHousehold.getFloods().add(flood);
                                break;
                            }
                        }
                    }
                    break;
                }
            }

        }
        return floodsAlertByHousehold;
    }
}