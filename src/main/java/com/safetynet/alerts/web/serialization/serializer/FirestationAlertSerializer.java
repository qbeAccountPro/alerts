package com.safetynet.alerts.web.serialization.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;

/**
 * Some javadoc.
 * 
 * Custom serializer for FirestationAlert objects when serializing fire station
 * data.
 * This serializer converts a FirestationAlert object to JSON format with
 * specific fields.
 */
public class FirestationAlertSerializer extends StdSerializer<FirestationAlert> {

  public FirestationAlertSerializer(Class<FirestationAlert> t) {
    super(t);
  }

  /**
   * Some javadoc.
   * 
   * Serialize a single FirestationAlert object to JSON format with specific
   * fields.
   *
   * @param firestationAlert The FirestationAlert object to be serialized.
   * @param gen              The JsonGenerator to write JSON content.
   * @param provider         The SerializerProvider for the serialization process.
   */
  @Override
  public void serialize(FirestationAlert firestationAlert, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("firstName", firestationAlert.getFirstName());
      gen.writeStringField("lastName", firestationAlert.getLastName());
      gen.writeStringField("address", firestationAlert.getAddress());
      gen.writeStringField("phone", firestationAlert.getPhone());
      gen.writeEndObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize a list of FirestationAlert objects to JSON format with specific
   * fields.
   *
   * @param FirestationAlerts The list of FirestationAlert objects to be
   *                          serialized.
   * @param gen               The JsonGenerator to write JSON content.
   * @param provider          The SerializerProvider for the serialization
   *                          process.
   */
  public void serializeList(List<FirestationAlert> FirestationAlerts, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (FirestationAlert FirestationAlert : FirestationAlerts) {
        serialize(FirestationAlert, gen, provider);
      }
      gen.writeEndArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}