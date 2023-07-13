package com.safetynet.alerts;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.alerts.web.dao.FirestationDao;
import com.safetynet.alerts.web.dao.MedicalrecordDao;
import com.safetynet.alerts.web.dao.PersonDao;
import com.safetynet.alerts.web.model.Firestations;
import com.safetynet.alerts.web.model.Medicalrecords;
import com.safetynet.alerts.web.model.ModelWrapper;
import com.safetynet.alerts.web.model.Persons;

import java.io.InputStream;
import java.util.List;

/* 

import springfox.documentation.swagger2.annotations.EnableSwagger2; */

@SpringBootApplication
/* @EnableSwagger2 */
public class AlertsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlertsApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(PersonDao personDao, FirestationDao firestationDao,MedicalrecordDao medicalrecordDao ) {
		return args -> {
			ObjectMapper objectMapper = new ObjectMapper();
			InputStream jsonPath = TypeReference.class.getResourceAsStream("/data.json");
			ModelWrapper modelWrapper = objectMapper.readValue(jsonPath, ModelWrapper.class);
			try {
				List<Persons> persons = modelWrapper.getPersons();
				List<Firestations> firestations = modelWrapper.getFirestations();
				List<Medicalrecords> medicalrecords = modelWrapper.getMedicalrecords();
				personDao.saveAll(persons);
				firestationDao.saveAll(firestations);
				medicalrecordDao.saveAll(medicalrecords);
			} catch (Exception e) {
				System.out.println("Unable to save persons : " + e.getMessage());
			}
			System.out.println("Sacve: ");

		};
	}

}
