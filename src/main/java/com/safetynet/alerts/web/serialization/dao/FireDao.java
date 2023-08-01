package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.Fire;

public class FireDao extends StdSerializer<Fire> {

    public FireDao(Class<Fire> t) {
        super(t);

    }

    @Override
    public void serialize(Fire fire, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartObject();
            gen.writeStringField("lastName", fire.getLastName());
            gen.writeStringField("phone", fire.getPhone());
            gen.writeNumberField("age", fire.getAge());
            gen.writeStartArray("medications");
            for (String medication : fire.getMedications()) {
                gen.writeString(medication);
            }
            gen.writeEndArray();
            gen.writeStartArray("allergies");
            for (String allergie : fire.getAllergies()) {
                gen.writeString(allergie);
            }
            gen.writeEndArray();
            gen.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeList(List<Fire> fires, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (Fire fire : fires) {
                serialize(fire, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
