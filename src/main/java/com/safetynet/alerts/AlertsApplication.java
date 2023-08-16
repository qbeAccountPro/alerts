package com.safetynet.alerts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.dao.MedicalRecordDao;
import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.deserialization.Deserialization;
import com.safetynet.alerts.web.model.Firestation;
import com.safetynet.alerts.web.model.MedicalRecord;
import com.safetynet.alerts.web.model.Person;

import java.io.InputStream;
import java.util.List;

/**
 * Some javadoc.
 * Main class of the Safetynet Alerts application. 
 */
@SpringBootApplication
@EnableWebMvc
public class AlertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertsApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonDao personDao, FirestationDao firestationDao, MedicalRecordDao medicalrecordDao) {
		return args -> {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream jsonPath = TypeReference.class.getResourceAsStream("/data.json");
			Deserialization modelWrapper = objectMapper.readValue(jsonPath, Deserialization.class);
			try {
				List<Person> persons = modelWrapper.getPersons();
				List<Firestation> firestations = modelWrapper.getFirestations();
				List<MedicalRecord> medicalrecords = modelWrapper.getMedicalrecords();
				personDao.saveAll(persons);
				firestationDao.saveAll(firestations);
				medicalrecordDao.saveAll(medicalrecords);
			} catch (Exception e) {
				System.out.println("Unable to start application : " + e.getMessage());
			}
			System.out.println("Application started: ");
		};
	}

}
