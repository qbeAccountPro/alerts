package com.safetynet.alerts.web.serialization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.ChildAlert;

public class ChildAlertServiceTest {
    @Test
    void testGetChildAlertListFromPersonList() {
        ChildAlertService childAlertService = new ChildAlertService();

        List<Person> persons = new ArrayList<>();
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        Person person1 = new Person();
        person1.setFirstName("Quentin");
        person1.setLastName("Beraud");
        persons.add(person1);

        Person person2 = new Person();
        person2.setFirstName("Bruce");
        person2.setLastName("Wayne");
        persons.add(person2);

        MedicalRecord medicalRecord1 = new MedicalRecord();
        medicalRecord1.setFirstName("Quentin");
        medicalRecord1.setLastName("Beraud");
        medicalRecord1.setBirthdate("08/08/2005");
        medicalRecords.add(medicalRecord1);

        MedicalRecord medicalRecord2 = new MedicalRecord();
        medicalRecord2.setFirstName("Bruce");
        medicalRecord2.setLastName("Wayne");
        medicalRecord2.setBirthdate("08/08/2010");

        medicalRecords.add(medicalRecord2);

        List<ChildAlert> expectedChildren = new ArrayList<>();
        ChildAlert childAlert1 = new ChildAlert();
        childAlert1.setFirstName("Quentin");
        childAlert1.setLastName("Beraud");
        childAlert1.setAge(18);
        expectedChildren.add(childAlert1);

        ChildAlert childAlert2 = new ChildAlert();
        childAlert2.setFirstName("Bruce");
        childAlert2.setLastName("Wayne");
        childAlert2.setAge(13);
        expectedChildren.add(childAlert2);

        List<ChildAlert> actualChildren = childAlertService.getChildAlertListFromPersonList(persons, medicalRecords);

        assertEquals(expectedChildren, actualChildren);
    }
}
