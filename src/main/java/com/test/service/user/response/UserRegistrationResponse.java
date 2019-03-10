package com.test.service.user.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

/**
 * user registration response which will be sent to caller.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRegistrationResponse {
    /**
     * contains key-value pairs, where key is field name and value is field value.
     */
    @Setter @Getter
    private Map<String, String> errors = Maps.newHashMap();
    /**
     * message to the caller
     */
    @Setter @Getter
    private String message;
    /**
     * return the status of the request to caller.
     * {@link HttpStatus#BAD_REQUEST} --> when user input is invalid
     * {@link HttpStatus#CONFLICT} --> when user is already existing.
     * {@link HttpStatus#FORBIDDEN} --> when user is blacklisted.
     * {@link HttpStatus#CREATED} --> when user is successfully registered.
     */
    @Setter @Getter
    private HttpStatus httpStatus;
}
