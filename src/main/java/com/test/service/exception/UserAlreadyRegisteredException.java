package com.test.service.exception;

import lombok.Getter;
import com.test.service.user.response.UserRegistrationResponse;

/**
 * Exception thrown when user is already registered.
 */
public class UserAlreadyRegisteredException extends RuntimeException {
    @Getter
    private UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();

    public UserAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
        userRegistrationResponse.setMessage(errorMessage);
    }
}
