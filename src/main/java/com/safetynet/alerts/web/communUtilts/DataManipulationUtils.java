package com.safetynet.alerts.web.communUtilts;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Some javadoc.
 * 
 * This class contains different utility methods for modified or checking
 * strings.
 */
public class DataManipulationUtils {

  /**
   * Some javadoc.
   * 
   * Updates an object with the non-null properties from a new object.
   *
   * @param oldObject The original object to be updated.
   * @param newObject The new object from which non-null properties are extracted
   *                  for updating.
   * @param <T>       The type of the objects being updated.
   * @return A new object with the non-null properties updated from the new
   *         object.
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
      return null;
    }
  }

  /**
   * Some javadoc.
   * 
   * Normalizes a string by removing diacritical marks and converting it to
   * lowercase.
   *
   * @param input The input string to be normalized.
   * @return The normalized string with diacritical marks removed and converted to
   *         lowercase.
   */
  public static String normalizeString(String input) {
    return Normalizer.normalize(input, Normalizer.Form.NFD)
        .replaceAll("\\p{M}", "")
        .toLowerCase();
  }

  /**
   * Some javadoc.
   * Converts a birthdate string in the format "MM/dd/yyyy" to the age in years.
   *
   * @param birthdate The birthdate string to convert.
   * @return The age in years calculated from the birthdate.
   */
  public int convertBirthdateToAge(String birthdate) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate dateOfBirth = LocalDate.parse(birthdate, formatter);
    LocalDate currentDate = LocalDate.now();
    return Period.between(dateOfBirth, currentDate).getYears();
  }

  /**
   * Some javadoc.
   * 
   * Checks if all fields of the object are null, except for the "id" field.
   *
   * @param object The object to check.
   * @return True if all fields (except "id") of the object are null, or if the
   *         object itself is null; otherwise, False.
   */
  public boolean areAllFieldsNullExceptId(Object object) {
    if (object == null) {
      return true;
    }
    Field[] fields = object.getClass().getDeclaredFields();
    try {
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        if (field.getName().equals("id")) {
          continue;
        }
        field.setAccessible(true);
        Object value = field.get(object);
        if (value != null) {
          return false;
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Some javadoc.
   * 
   * Checks if at least one field of the object (other than "id") is null.
   *
   * @param object The object to check.
   * @return True if at least one field (other than "id") of the object is null,
   *         or if the object itself is null; otherwise, False.
   */
  public boolean areFieldsNullExceptId(Object object) {
    if (object == null) {
      return true;
    }
    Field[] fields = object.getClass().getDeclaredFields();
    try {
      for (Field field : fields) {
        if (Modifier.isStatic(field.getModifiers())) {
          continue;
        }
        if (field.getName().equals("id")) {
          continue;
        }
        field.setAccessible(true);
        Object value = field.get(object);
        if (value == null) {
          return true;
        }
      }
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return true;
    }
    return false;
  }

  /**
   * Some javadoc.
   * Retrieves the name of the current method being executed.
   *
   * @return The name of the current method.
   */
  public static String getCurrentMethodName() {
    StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
    return stackTraceElement.getMethodName();
  }
}