package com.test.service.exception;

import com.test.service.user.response.UserRegistrationResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when user inputs a invalid data.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUserInputException extends RuntimeException {

    /**
     * bean which stores the invalid user input data with it's corresponding field as a key
     */
    @Getter
    private UserRegistrationResponse userRegistrationResponse;

    public InvalidUserInputException(UserRegistrationResponse userRegistrationResponse) {
        super();
        this.userRegistrationResponse  = userRegistrationResponse;
    }
}
