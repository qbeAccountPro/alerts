package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.Flood;
import com.safetynet.alerts.web.serialization.model.FloodAddress;

/**
 * Some javadoc.
 * Custom serializer for FloodAddress objects.
 * This serializer converts a FloodAddress object to JSON format with specific
 * fields.
 */
public class FloodAddressDao extends StdSerializer<FloodAddress> {

    public FloodAddressDao(Class<FloodAddress> t) {
        super(t);
    }

    /**
     * Some javadoc.
     * Serialize a single FloodAddress object to JSON format with specific fields.
     *
     * @param floodAddress The FloodAddress object to be serialized.
     * @param gen          The JsonGenerator to write JSON content.
     * @param provider     The SerializerProvider for the serialization process.
     */
    @Override
    public void serialize(FloodAddress floodAddress, JsonGenerator gen, SerializerProvider provider) {
        try {

            gen.writeStartArray();
            for (Flood flood : floodAddress.getFlood()) {

                gen.writeStartObject();
                gen.writeStringField("address", flood.getAddress());
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
     * Serialize a list of FloodAddress objects to JSON format with specific fields.
     *
     * @param floodAddresses The list of FloodAddress objects to be serialized.
     * @param gen            The JsonGenerator to write JSON content.
     * @param provider       The SerializerProvider for the serialization process.
     */
    public void serializeList(List<FloodAddress> floodAddresses, JsonGenerator gen,
            SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (FloodAddress floodAddress : floodAddresses) {
                serialize(floodAddress, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
