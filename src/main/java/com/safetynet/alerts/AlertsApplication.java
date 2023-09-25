package com.safetynet.alerts;

import java.io.InputStream;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.dao.HouseholdDao;
import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.deserialization.Deserialization;
import com.safetynet.alerts.web.deserialization.model.FirestationDeserialization;
import com.safetynet.alerts.web.deserialization.model.MedicalRecordDeserialization;
import com.safetynet.alerts.web.deserialization.model.PersonDeserialization;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.Household;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;
import com.safetynet.alerts.web.service.ConvertModelService;

/**
 * Some javadoc.
 * 
 * Main class of the Safetynet Alerts application.
 */
@SpringBootApplication
public class AlertsApplication {
	private ConvertModelService convertModel = new ConvertModelService();

	public static void main(String[] args) {
		SpringApplication.run(AlertsApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonDao personDao, FirestationDao firestationDao, MedicalRecordDao medicalrecordDao,
			HouseholdDao householdDao) {
		return args -> {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream jsonPath = TypeReference.class.getResourceAsStream("/data.json");
			Deserialization modelWrapper = objectMapper.readValue(jsonPath, Deserialization.class);
			try {
				// Deserialization :
				List<PersonDeserialization> personsDeserialization = modelWrapper.getPersons();
				List<FirestationDeserialization> firestationsDeserialization = modelWrapper.getFirestations();
				List<MedicalRecordDeserialization> medicalrecordsDeserialization = modelWrapper.getMedicalrecords();

				// Convert model :
				List<Household> households = convertModel.getHouseholds(personsDeserialization,
						firestationsDeserialization);
				householdDao.saveAll(households);

				List<Firestation> firestations = convertModel.getFirestations(firestationsDeserialization,
						householdDao.findAll());
				firestationDao.saveAll(firestations);

				List<Person> persons = convertModel.getPersons(personsDeserialization, householdDao.findAll());
				personDao.saveAll(persons);
				List<MedicalRecord> medicalRecords = convertModel.getMedicalRecords(medicalrecordsDeserialization,
						personDao.findAll());
				medicalrecordDao.saveAll(medicalRecords);
			} catch (Exception e) {
				System.out.println("Unable to start application : " + e.getMessage());
			}
			System.out.println("Application started: ");
		};
	}
}