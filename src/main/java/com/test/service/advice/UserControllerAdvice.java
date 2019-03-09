package com.test.service.advice;

import com.test.service.exception.InvalidUserInputException;
import com.test.service.exception.UserAlreadyRegisteredException;
import com.test.service.exception.UserBlacklistedException;
import com.test.service.user.response.UserRegistrationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Advice which handles the registration service exceptions
 */
@ControllerAdvice
@RequestMapping(produces = "application/json")
public class UserControllerAdvice {

    /**
     * Handler to handle the {@link InvalidUserInputException}
     * This will return {@link HttpStatus#BAD_REQUEST} status.
     *
     * @param ex InvalidUserInputException
     * @return {@link UserRegistrationResponse}
     */
    @ExceptionHandler(InvalidUserInputException.class)
    protected ResponseEntity<UserRegistrationResponse> handleInvalidUserInput(InvalidUserInputException ex) {
        ex.getUserRegistrationResponse().setHttpStatus(HttpStatus.BAD_REQUEST);
        ex.getUserRegistrationResponse().setMessage("User Registration failed. Please provide valid input.");
        return new ResponseEntity<>(ex.getUserRegistrationResponse(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handler to handle the {@link UserAlreadyRegisteredException}
     * This will return {@link HttpStatus#CONFLICT} status.
     *
     * @param ex UserAlreadyRegisteredException
     * @return {@link UserRegistrationResponse}
     */
    @ExceptionHandler(UserAlreadyRegisteredException.class)
    protected ResponseEntity<UserRegistrationResponse> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException ex) {
        ex.getUserRegistrationResponse().setHttpStatus(HttpStatus.CONFLICT);
        return new ResponseEntity<>(ex.getUserRegistrationResponse(), HttpStatus.CONFLICT);
    }

    /**
     * Handler to handle the {@link UserBlacklistedException}
     * This will return {@link HttpStatus#FORBIDDEN} status.
     *
     * @param ex UserblacklistedException
     * @return {@link UserRegistrationResponse}
     */
    @ExceptionHandler(UserBlacklistedException.class)
    protected ResponseEntity<UserRegistrationResponse> handleUserBlockedException(UserBlacklistedException ex) {
        ex.getRegistrationResponse().setHttpStatus(HttpStatus.FORBIDDEN);
        return new ResponseEntity<>(ex.getRegistrationResponse(), HttpStatus.FORBIDDEN);
    }
}
