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
 * Custom serializer for PersonInfo objects.
 * This serializer converts a PersonInfo object to JSON format with specific
 * fields.
 */
public class PersonInfoDao extends StdSerializer<PersonInfoAlert> {

    public PersonInfoDao(Class<PersonInfoAlert> t) {
        super(t);
    }

    /**
     * Some javadoc.
     * 
     * Serialize a single PersonInfo object to JSON format with specific fields.
     *
     * @param personInfo The PersonInfo object to be serialized.
     * @param gen        The JsonGenerator to write JSON content.
     * @param provider   The SerializerProvider for the serialization process.
     */
    @Override
    public void serialize(PersonInfoAlert personInfo, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartObject();
            gen.writeStringField("lastName", personInfo.getLastName());
            gen.writeStringField("address", personInfo.getAddress());
            gen.writeNumberField("age", personInfo.getAge());
            gen.writeStringField("phone", personInfo.getMail());
            gen.writeFieldName("medications");
            gen.writeStartArray();
            if (personInfo.getMedications() != null) {
                for (String medication : personInfo.getMedications()) {
                    gen.writeString(medication);
                }
            }
            gen.writeEndArray();
            gen.writeFieldName("allergies");
            gen.writeStartArray();
            if (personInfo.getAllergies() != null) {
                for (String allergie : personInfo.getAllergies()) {
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
     * Serialize a list of PersonInfo objects to JSON format with specific fields.
     *
     * @param personsInfos The list of PersonInfo objects to be serialized.
     * @param gen          The JsonGenerator to write JSON content.
     * @param provider     The SerializerProvider for the serialization process.
     */
    public void serializeList(List<PersonInfoAlert> personsInfos, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (PersonInfoAlert personInfo : personsInfos) {
                serialize(personInfo, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}