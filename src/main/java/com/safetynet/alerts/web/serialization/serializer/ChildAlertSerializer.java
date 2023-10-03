package com.safetynet.alerts.web.serialization.serializer;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.ChildAlert;

/**
 * Some javadoc.
 * 
 * Custom serializer for ChildAlert objects.
 * This serializer converts a ChildAlert object to JSON format.
 */
public class ChildAlertSerializer extends StdSerializer<ChildAlert> {

  public ChildAlertSerializer(Class<ChildAlert> t) {
    super(t);
  }

  /**
   * Some javadoc.
   * Serialize a single ChildAlert object to JSON format.
   *
   * @param childAlert The ChildAlert object to be serialized.
   * @param gen        The JsonGenerator to write JSON content.
   * @param provider   The SerializerProvider for the serialization process.
   */
  @Override
  public void serialize(ChildAlert childAlert, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartObject();
      gen.writeStringField("firstName", childAlert.getFirstName());
      gen.writeStringField("lastName", childAlert.getLastName());
      gen.writeNumberField("age", childAlert.getAge());
      gen.writeEndObject();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Some javadoc.
   * 
   * Serialize a list of ChildAlert objects to JSON format.
   *
   * @param childrenAlert The list of ChildAlert objects to be serialized.
   * @param gen           The JsonGenerator to write JSON content.
   * @param provider      The SerializerProvider for the serialization process.
   */
  public void serializeList(List<ChildAlert> childrenAlert, JsonGenerator gen, SerializerProvider provider) {
    try {
      gen.writeStartArray();
      for (ChildAlert childAlert : childrenAlert) {
        serialize(childAlert, gen, provider);
      }
      gen.writeEndArray();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}