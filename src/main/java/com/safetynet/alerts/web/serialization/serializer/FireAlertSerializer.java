package com.safetynet.alerts.web.serialization.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.FireAlert;

/**
 * Some javadoc.
 * 
 * Custom serializer for FireAlert objects.
 * This serializer converts a FireAlert object to JSON format with specific
 * fields.
 */
public class FireAlertSerializer extends StdSerializer<FireAlert> {

  public FireAlertSerializer(Class<FireAlert> t) {
    super(t);
  }

  /**
   * Some javadoc.
   * 
   * Serialize a single FireAlert object to JSON format with specific fields.
   *
   * @param fire     The FireAlert object to be serialized.
   * @param gen      The JsonGenerator to write JSON content.
   * @param provider The SerializerProvider for the serialization process.
   */
  @Override
  public void serialize(FireAlert fire, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("lastName", fire.getLastName());
      gen.writeStringField("phone", fire.getPhone());
      gen.writeNumberField("age", fire.getAge());
      gen.writeFieldName("medications");
      gen.writeStartArray();
      if (fire.getMedications() != null) {
        for (String medication : fire.getMedications()) {
          gen.writeString(medication);
        }
      }
      gen.writeEndArray();
      gen.writeFieldName("allergies");
      gen.writeStartArray();
      if (fire.getAllergies() != null) {
        for (String allergie : fire.getAllergies()) {
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
   * Serialize a list of FireAlert objects to JSON format with specific fields.
   *
   * @param fires    The list of FireAlert objects to be serialized.
   * @param gen      The JsonGenerator to write JSON content.
   * @param provider The SerializerProvider for the serialization process.
   */
  public void serializeList(List<FireAlert> fires, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (FireAlert fire : fires) {
        serialize(fire, gen, provider);
      }
      gen.writeEndArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}