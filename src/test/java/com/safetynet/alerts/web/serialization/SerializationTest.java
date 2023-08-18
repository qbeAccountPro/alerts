package com.safetynet.alerts.web.serialization;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.serialization.dao.*;
import com.safetynet.alerts.web.serialization.model.ChildAlert;
import com.safetynet.alerts.web.serialization.model.Fire;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;
import com.safetynet.alerts.web.serialization.model.PersonInfo;

@ExtendWith(MockitoExtension.class)
public class SerializationTest {

    @Mock
    private ChildAlertDao childAlertDao;
    @Mock
    private CommunityEmailDao communityEmailDao;
    @Mock
    private FireDao fireDao;
    @Mock
    private FirestationDao firestationDao;
    @Mock
    private FloodAddressDao floodAddressDao;
    @Mock
    private OtherResidentsDao otherResidentsDao;
    @Mock
    private PersonInfoDao personeInfoDao;
    @Mock
    private PhoneAlertDao phoneAlertDao;

    @InjectMocks
    private Serialization serialization;

    private Person person_1, person_2;
    private List<Person> persons;
    private String method, argument;

    @BeforeEach
    public void setUp() {

        person_1 = new Person(1, "Mick", "Muse", "ici", "iciLand", "6900", "06 66 66 66 66",
                "mickMuse@MuseMick");
        person_2 = new Person(2, "Jack", "Jack", "Rue du JackyLand", "Jacky-island", "Jack Jack Jack", "00 00 02 00 00",
                "JackJack@Jack");
        persons = Arrays.asList(person_1, person_2);
        method = "someMethod";
        argument = "someArgument";
    }

    /**
     * SomeJavadoc.
     * Test 'ChildAlertSerialization' method.
     */
    @Test
    void testChildAlertSerialization() {
        ChildAlert childAlert_1 = new ChildAlert("Quentin", "Beraud", 13);
        ChildAlert childAlert_2 = new ChildAlert("George", "Beraud", 3);
        List<ChildAlert> children = Arrays.asList(childAlert_1, childAlert_2);
        ChildAlert childAlert_3 = new ChildAlert("Quentin", "Beraud", 43);
        ChildAlert childAlert_4 = new ChildAlert("George", "Beraud", 53);
        List<ChildAlert> adults = Arrays.asList(childAlert_3, childAlert_4);

        serialization.childAlertSerialization(children, adults, method, argument);

        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("children"));
            assertTrue(fileContent.contains("Quentin"));
            assertTrue(fileContent.contains("George"));
            assertTrue(fileContent.contains("adults"));
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }

    /**
     * SomeJavadoc.
     * Test 'CommunityEmailSerialization' method.
     */
    @Test
    void testCommunityEmailSerialization() {
        serialization.communityEmailSerialization(persons, method, argument);

        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("emails"));
            assertTrue(fileContent.contains("mickMuse@MuseMick"));
            assertTrue(fileContent.contains("JackJack@Jack"));
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }

    /**
     * SomeJavadoc.
     * Test 'FireSerialization' method.
     */
    @Test
    void testFireSerialization() {
        Fire fire_1 = new Fire("Moti", "06 66 66 66 66", 35, null, null);
        Fire fire_2 = new Fire("Hachu", "06 68 68 68 68", 35, null, null);
        List<Fire> fires = Arrays.asList(fire_1, fire_2);
        String firestationNumber = "1";

        serialization.fireSerialization(fires, firestationNumber, method, argument);

        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("persons"));
            assertTrue(fileContent.contains("station"));
            assertTrue(fileContent.contains("stationServing"));
            assertTrue(fileContent.contains("Moti"));
            assertTrue(fileContent.contains("Hachu"));
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }

    /**
     * SomeJavadoc.
     * Test 'FirestationSerialization' method.
     */
    @Test
    void testFirestationSerialization() {
        int minorsNumber = 0, adultsNumber = 2;
        serialization.firestationSerialization(persons, method, argument, minorsNumber, adultsNumber);

        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("persons"));
            assertTrue(fileContent.contains("adults"));
            assertTrue(fileContent.contains("minors"));
            assertTrue(fileContent.contains("counters"));
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }

    /**
     * SomeJavadoc.
     * Test 'FloodSerialization' method.
     */
    @Test
    void testFloodSerialization() {
        Flood flood_1 = new Flood("qb", "be", "123 Rue par ici", 13, null, null);
        Flood flood_2 = new Flood("kb", "be", "123 Rue par ici", 26, null, null);
        Flood flood_3 = new Flood("jb", "dm", "456 Chemin du", 46, null, null);
        Flood flood_4 = new Flood("pb", "dm", "456 Chemin du", 45, null, null);

        List<Flood> floods_1 = Arrays.asList(flood_1, flood_2);
        List<Flood> floods_2 = Arrays.asList(flood_3, flood_4);

        FloodAddress floodAddress_1 = new FloodAddress("123 Rue par ici", floods_1);
        FloodAddress floodAddress_2 = new FloodAddress("456 Chemin du", floods_2);

        List<FloodAddress> floodsAddresses = Arrays.asList(floodAddress_1, floodAddress_2);

        serialization.floodSerialization(floodsAddresses, method, argument);

        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());

        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("persons"));
            for (FloodAddress floodsAddress : floodsAddresses) {
                assertTrue(fileContent.contains(floodsAddress.getAddress()));
                for (Flood flood : floodsAddress.getFlood()) {
                    assertTrue(fileContent.contains(flood.getAddress()));
                    assertTrue(fileContent.contains(flood.getLastName()));
                    assertTrue(fileContent.contains(flood.getPhone()));
                }
            }
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }

    }

    /**
     * SomeJavadoc.
     * Test 'PersonInfoSerialization' method.
     */
    @Test
    void testPersonInfoSerialization() {
        PersonInfo personInfo = new PersonInfo("Quentin", "Beraud", "qbe@BE.fr", 60, null, null);
        List<PersonInfo> personsInfos = Arrays.asList(personInfo);
        String firstName = "someFtName";
        String lastName = "someLtName";
        serialization.personInfoSerialization(personsInfos, method, firstName, lastName);
        String fileName = serialization.setFileNameString(method, firstName + "_" + lastName);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("persons"));
            for (PersonInfo personInfoX : personsInfos) {
                assertTrue(fileContent.contains(personInfoX.getLastName()));
                assertTrue(fileContent.contains(personInfoX.getMail()));
            }
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }

    /**
     * SomeJavadoc.
     * Test 'PhoneAlertSerialization' method.
     */
    @Test
    void testPhoneAlertSerialization() {
        serialization.phoneAlertSerialization(persons, method, argument);
        String fileName = serialization.setFileNameString(method, argument);
        File file = new File(fileName);
        assertTrue(new File(fileName).exists());
        try {
            String fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            assertTrue(fileContent.contains("phones"));
            assertTrue(fileContent.contains(person_1.getPhone()));
            file.delete();
            assertFalse(file.exists());
        } catch (IOException e) {
            fail("Failed to read file: " + e.getMessage());
        }
    }
}
