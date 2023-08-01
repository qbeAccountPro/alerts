package com.safetynet.alerts.web.serialization.dao;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.safetynet.alerts.web.serialization.model.ChildAlert;

public class ChildAlertDao extends StdSerializer<ChildAlert> {


    public ChildAlertDao(Class<ChildAlert> t) {
        super(t);
    }

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
