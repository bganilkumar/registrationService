package com.test.service.exception;

import lombok.Getter;

import com.test.service.user.response.UserRegistrationResponse;

/**
 * Exception thrown when user inputs a invalid data.
 */
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
