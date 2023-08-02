package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.PersonInfo;

public class PersoneInfoDao extends StdSerializer<PersonInfo> {

    public PersoneInfoDao(Class<PersonInfo> t) {
        super(t);
    }

    @Override
    public void serialize(PersonInfo personInfo, JsonGenerator gen, SerializerProvider provider) {
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

    public void serializeList(List<PersonInfo> personsInfos, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (PersonInfo personInfo : personsInfos) {
                serialize(personInfo, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
