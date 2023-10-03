package com.safetynet.alerts.web.serialization.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.FloodAlert;
import com.safetynet.alerts.web.serialization.model.FloodAlertByHousehold;

/**
 * Some javadoc.
 * 
 * Custom serializer for FloodAlertByHousehold objects.
 * This serializer converts a FloodAlertByHousehold object to JSON format with
 * specific
 * fields.
 */
public class FloodAlertSerializer extends StdSerializer<FloodAlertByHousehold> {

  public FloodAlertSerializer(Class<FloodAlertByHousehold> t) {
    super(t);
  }

  /**
   * Some javadoc.
   * 
   * Serialize a single FloodAlertByHousehold object to JSON format with specific
   * fields.
   *
   * @param FloodAlertByHousehold The FloodAlertByHousehold object to be
   *                              serialized.
   * @param gen                   The JsonGenerator to write JSON content.
   * @param provider              The SerializerProvider for the serialization
   *                              process.
   */
  @Override
  public void serialize(FloodAlertByHousehold floodAlertByHousehold, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (FloodAlert flood : floodAlertByHousehold.getFloods()) {
        gen.writeStartObject();
        gen.writeStringField("address", floodAlertByHousehold.getHousehold().getAddress());
        gen.writeStringField("lastName", flood.getLastName());
        gen.writeNumberField("age", flood.getAge());
        gen.writeStringField("phone", flood.getPhone());
        gen.writeFieldName("medications");
        gen.writeStartArray();
        if (flood.getMedications() != null) {
          for (String medication : flood.getMedications()) {
            gen.writeString(medication);
          }
        }
        gen.writeEndArray();
        gen.writeFieldName("allergies");
        gen.writeStartArray();
        if (flood.getAllergies() != null) {
          for (String allergie : flood.getAllergies()) {
            gen.writeString(allergie);
          }
        }
        gen.writeEndArray();
        gen.writeEndObject();
      }
      gen.writeEndArray();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize a list of FloodAlertByHousehold objects to JSON format with
   * specific fields.
   *
   * @param FloodsAlertByHousehold The list of FloodAlertByHousehold objects to be
   *                               serialized.
   * @param gen                    The JsonGenerator to write JSON content.
   * @param provider               The SerializerProvider for the serialization
   *                               process.
   */
  public void serializeList(List<FloodAlertByHousehold> FloodsAlertByHousehold, JsonGenerator gen,
      SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (FloodAlertByHousehold FloodAlertByHousehold : FloodsAlertByHousehold) {
        serialize(FloodAlertByHousehold, gen, provider);
      }
      gen.writeEndArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}