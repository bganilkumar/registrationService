package com.test.service.exception;

import com.test.service.user.response.UserRegistrationResponse;
import lombok.Getter;

/**
 * Exception thrown when user is blacklisted.
 */
public class UserBlacklistedException extends RuntimeException {
    @Getter
    private UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
    public UserBlacklistedException(String errorMessage) {
        super(errorMessage);
        registrationResponse.setMessage(errorMessage);
    }
}
