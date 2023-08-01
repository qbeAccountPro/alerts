package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.model.Person;

public class PhoneAlertDao extends StdSerializer<Person> {


    public PhoneAlertDao(Class<Person> t) {
        super(t);

    }

    @Override
    public void serialize(Person person, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartObject();
            gen.writeStringField("phone", person.getPhone());
            gen.writeEndObject();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serializeList(List<Person> persons, JsonGenerator gen, SerializerProvider provider) {
        try {
            gen.writeStartArray();
            for (Person person : persons) {
                serialize(person, gen, provider);
            }
            gen.writeEndArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
