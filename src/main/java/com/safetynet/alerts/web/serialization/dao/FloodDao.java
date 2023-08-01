package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.Flood;

public class FloodDao extends StdSerializer<Flood> {

    public FloodDao(Class<Flood> t) {
        super(t);

    }

    @Override
    public void serialize(Flood flood, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartObject();
            gen.writeStringField("lastName", flood.getLastName());
            gen.writeStringField("address", flood.getPhone());
            gen.writeNumberField("age", flood.getAge());
            gen.writeStringField("phone", flood.getPhone());
            gen.writeStartArray("medications");
            for (String medication : flood.getMedications()) {
                gen.writeString(medication);
            }
            gen.writeEndArray();
            gen.writeStartArray("allergies");
            for (String allergie : flood.getAllergies()) {
                gen.writeString(allergie);
            }
            gen.writeEndArray();
            gen.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeList(List<Flood> floods, List<String> addresses, JsonGenerator gen,
            SerializerProvider provider) {
        try { // TODO REGROUPER PAR ADRESSE
            gen.writeStartArray();
            for (String address : addresses) {
                gen.writeStringField("lastName", address);
                for (Flood flood : floods) {
                    if (flood.getAddress() == address) {
                        serialize(flood, gen, provider);
                    }
                }
            }

            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
