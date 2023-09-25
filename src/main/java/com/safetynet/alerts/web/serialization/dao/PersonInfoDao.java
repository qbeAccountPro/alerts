package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.PersonInfoAlert;

/**
 * Some javadoc.
 * 
 * Custom serializer for PersonInfoAlert objects.
 * This serializer converts a PersonInfoAlert object to JSON format with
 * specific
 * fields.
 */
public class PersonInfoDao extends StdSerializer<PersonInfoAlert> {

  public PersonInfoDao(Class<PersonInfoAlert> t) {
    super(t);
  }

  /**
   * Some javadoc.
   * 
   * Serialize a single PersonInfoAlert object to JSON format with specific
   * fields.
   *
   * @param PersonInfoAlert The PersonInfoAlert object to be serialized.
   * @param gen             The JsonGenerator to write JSON content.
   * @param provider        The SerializerProvider for the serialization process.
   */
  @Override
  public void serialize(PersonInfoAlert personInfoAlert, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("lastName", personInfoAlert.getLastName());
      gen.writeStringField("address", personInfoAlert.getAddress());
      gen.writeNumberField("age", personInfoAlert.getAge());
      gen.writeStringField("email", personInfoAlert.getMail());
      gen.writeFieldName("medications");
      gen.writeStartArray();
      if (personInfoAlert.getMedications() != null) {
        for (String medication : personInfoAlert.getMedications()) {
          gen.writeString(medication);
        }
      }
      gen.writeEndArray();
      gen.writeFieldName("allergies");
      gen.writeStartArray();
      if (personInfoAlert.getAllergies() != null) {
        for (String allergie : personInfoAlert.getAllergies()) {
          gen.writeString(allergie);
        }
      }
      gen.writeEndArray();

      gen.writeEndObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize a list of PersonInfoAlert objects to JSON format with specific
   * fields.
   *
   * @param personsInfoAlert The list of PersonInfoAlert objects to be serialized.
   * @param gen              The JsonGenerator to write JSON content.
   * @param provider         The SerializerProvider for the serialization process.
   */
  public void serializeList(List<PersonInfoAlert> personsInfoAlert, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (PersonInfoAlert personInfo : personsInfoAlert) {
        serialize(personInfo, gen, provider);
      }
      gen.writeEndArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}