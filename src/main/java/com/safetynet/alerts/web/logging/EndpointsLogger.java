package com.safetynet.alerts.web.logging;

import org.springframework.http.ResponseEntity;
import org.tinylog.Logger;

import com.safetynet.alerts.web.httpResponse.ResponseBuilder;

/* 
 * Some javadoc.
 * 
 * This class represent all log generated during the endpoints.
 */
public class EndpointsLogger {

    private ResponseBuilder response = new ResponseBuilder();

    /**
     * Some javadoc.
     * 
     * Logs a request for the given method.
     * 
     * @param methodName The name of the method.
     */
    public void request(String methodName) {
        Logger.info("Request " + methodName + ".");
    }

    /**
     * Some javadoc.
     * 
     * Logs a request with an argument for the given method.
     * 
     * @param methodName The name of the method.
     * @param argument   The argument value.
     */
    public void request(String methodName, String argument) {
        Logger.info("Request " + methodName + " with this argument : " + argument + ".");
    }

    /**
     * Some javadoc.
     * 
     * Logs a request with two arguments for the given method.
     * 
     * @param methodName The name of the method.
     * @param argument1  The first argument value.
     * @param argument2  The second argument value.
     */
    public void request(String methodName, String argument1, String argument2) {
        Logger.info("Request " + methodName + " with this arguments : " + argument1 + " & " + argument2 + ".");
    }

    /**
     * Some javadoc.
     * 
     * Logs an incorrect content response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating incorrect content.
     */
    public ResponseEntity<String> incorrectContent(String methodName) {
        Logger.info("Answer " + methodName + " : content is incorrect.");
        return response.incorrectContent();
    }

    /**
     * Some javadoc.
     * 
     * Logs an argument with no match response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating no matching argument.
     */
    public ResponseEntity<String> argumentHasNoMatch(String methodName) {
        Logger.info("Answer " + methodName + " : argument has no match.");
        return response.hasNoMatch();
    }

    /**
     * Some javadoc.
     * 
     * Logs a successful content addition response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating successful addition.
     */
    public ResponseEntity<String> addedSuccessfully(String methodName) {
        Logger.info("Answer " + methodName + " : content added successfully.");
        return response.addedSuccessfully();
    }

    /**
     * Some javadoc.
     * 
     * Logs a successful content update response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating successful update.
     */
    public ResponseEntity<String> updatedSuccessfully(String methodName) {
        Logger.info("Answer " + methodName + " : content updated successfully.");
        return response.updatedSuccessfully();
    }

    /**
     * Some javadoc.
     * 
     * Logs an exception being thrown response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating an exception.
     */
    public ResponseEntity<String> threwAnException(String methodName) {
        Logger.error("Answer " + methodName + " : thew an exception.");
        return response.threwAnException();
    }

    /**
     * Some javadoc.
     * 
     * Logs a successful content deletion response.
     * 
     * @param methodName The name of the method.
     * @return A response indicating successful deletion.
     */
    public ResponseEntity<String> deletedSuccessfully(String methodName) {
        Logger.info("Answer " + methodName + " : deleted successfully.");
        return response.deletedSuccessfully();
    }

    /**
     * Some javadoc.
     * 
     * Logs a empty answer request.
     * 
     * @param methodName The name of the method.
     * @return A response indicating an empty answer.
     */
    public ResponseEntity<String> emptyAnswer(String methodeName) {
        Logger.info("Answer " + methodeName + " : empty answer.");
        return response.emptyAnswer();
    }

    /**
     * Some javadoc.
     * 
     * Logs a successfully answer.
     * 
     * @param methodName The name of the method.
     * @return A response indicating successful generation.
     */
    public ResponseEntity<String> successfullyGenerated(String methodeName) {
        Logger.info("Answer " + methodeName + " : successfully generated.");
        return response.successfullyGenerated();
    }

    public ResponseEntity<String> mappingBetwenAddressAndFirestationExits(String methodeName) {
        Logger.error("Answer " + methodeName + " : mapping betwen the address to the fire station already exists.");
        return response.mappingAlreadyExists();
    }

    public ResponseEntity<String> personExists(String methodeName) {
        Logger.error("Answer " + methodeName + " : a person with this first and last name already exists.");
        return response.mappingAlreadyExists();
    }

    public ResponseEntity<String> medicalRecordExists(String methodeName) {
        Logger.error("Answer " + methodeName + " : a medicalRecord with this first and last name already exists.");
        return response.mappingAlreadyExists();
    }
}