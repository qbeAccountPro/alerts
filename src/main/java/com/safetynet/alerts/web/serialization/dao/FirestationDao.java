package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.FirestationAlert;

/**
 * Some javadoc.
 * 
 * Custom serializer for Person objects when serializing fire station data.
 * This serializer converts a Person object to JSON format with specific fields.
 */
public class FirestationDao extends StdSerializer<FirestationAlert> {

    public FirestationDao(Class<FirestationAlert> t) {
        super(t);
    }

    /**
     * Some javadoc.
     * 
     * Serialize a single Person object to JSON format with specific fields.
     *
     * @param person   The Person object to be serialized.
     * @param gen      The JsonGenerator to write JSON content.
     * @param provider The SerializerProvider for the serialization process.
     */
    @Override
    public void serialize(FirestationAlert person, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartObject();
            gen.writeStringField("firstName", person.getFirstName());
            gen.writeStringField("lastName", person.getLastName());
            gen.writeStringField("address", person.getAddress());
            gen.writeStringField("phone", person.getPhone());
            gen.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Some javadoc.
     * 
     * Serialize a list of Person objects to JSON format with specific fields.
     *
     * @param persons  The list of Person objects to be serialized.
     * @param gen      The JsonGenerator to write JSON content.
     * @param provider The SerializerProvider for the serialization process.
     */
    public void serializeList(List<FirestationAlert> persons, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (FirestationAlert person : persons) {
                serialize(person, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}