package com.safetynet.alerts.web.service;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class BeanService {

    /**
     * Create an object an insert values on properties from new object unless if the
     * new value is null.
     * 
     * @param oldObject : Old object with old value.
     * @param newObject : New object with new value.
     * @return updateObject : Object with value from new object unless if the new
     *         value is null in which case we take old value.
     */
    public static <T> T updateBeanWithNotNullPropertiesFromNewObject(T oldObject, T newObject) {
        Class<?> genericClass = oldObject.getClass();

        try {
            Constructor<?> genericConstructor = genericClass.getDeclaredConstructor();
            genericConstructor.setAccessible(true);
            @SuppressWarnings("unchecked")
            T updateObject = (T) genericConstructor.newInstance();

            Field[] properties = genericClass.getDeclaredFields();

            for (Field propertie : properties) {
                propertie.setAccessible(true);

                Object oldValue = propertie.get(oldObject);
                Object newValue = propertie.get(newObject);

                if (newValue != null) {
                    propertie.set(updateObject, newValue);
                } else {
                    propertie.set(updateObject, oldValue);
                }
            }
            return updateObject;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String normalizeString(String input) {
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }

    public int convertBirthdateToAge(String birthdate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);
        LocalDate currentDate = LocalDate.now();
        return Period.between(dateOfBirth, currentDate).getYears();
    }

}