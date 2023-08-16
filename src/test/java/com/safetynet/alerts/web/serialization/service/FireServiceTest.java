package com.safetynet.alerts.web.serialization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.model.Fire;

public class FireServiceTest {
    @Test
    void testGetFireList() {
        FireService fireService = new FireService();

        List<Person> persons = new ArrayList<>();
        List<MedicalRecord> medicalRecords = new ArrayList<>();

        Person person1 = new Person(0, "Quentin", "Beraud", "ici", "Lyon", "69000", "06 66 66 66 66", "qbe.pro@yahoOo");
        Person person2 = new Person(0, "Bruce", "Wolly", "ah c'est là bas", "Paris", "00000", "06 68 88 88 88",
                "Bruce.Wolly@yahoOo");
        persons.add(person1);
        persons.add(person2);

        MedicalRecord medicalRecord1 = new MedicalRecord(1, "Quentin", "Beraud", "08/08/2005", null, null);
        MedicalRecord medicalRecord2 = new MedicalRecord(1, "Bruce", "Wolly", "01/01/2010", null, null);
        medicalRecords.add(medicalRecord1);
        medicalRecords.add(medicalRecord2);

        List<Fire> expectedfires = new ArrayList<>();
        Fire fire1 = new Fire("Beraud", "06 66 66 66 66", 18, null, null);
        Fire fire2 = new Fire("Wolly", "06 68 88 88 88", 13, null, null);
        expectedfires.add(fire1);
        expectedfires.add(fire2);

        List<Fire> actualFires = fireService.getFireList(persons, medicalRecords);

        assertEquals(expectedfires, actualFires);
    }
}
