package com.test.service.exception;

import com.test.service.user.response.UserRegistrationResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user is blacklisted.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserBlacklistedException extends RuntimeException {
    @Getter
    private UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
    public UserBlacklistedException(String errorMessage) {
        super(errorMessage);
        registrationResponse.setMessage(errorMessage);
    }
}
