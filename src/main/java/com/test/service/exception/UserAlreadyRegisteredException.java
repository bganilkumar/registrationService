package com.test.service.exception;

import com.test.service.user.response.UserRegistrationResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user is already registered.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyRegisteredException extends RuntimeException {
    @Getter
    private UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();

    public UserAlreadyRegisteredException(String errorMessage) {
        super(errorMessage);
        userRegistrationResponse.setMessage(errorMessage);
    }
}
