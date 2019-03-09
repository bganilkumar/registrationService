package com.test.service.validation;

import com.test.service.exception.InvalidUserInputException;
import com.test.service.user.request.UserRegistrationRequest;
import com.test.service.user.response.UserRegistrationResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

@Component
public class UserInputValidator {

    /**
     * alphanumeric regex pattern to validate username
     */
    private static final Pattern userNamePattern = Pattern.compile("^[a-zA-Z0-9]+$");

    /**
     * regex pattern to validate password.
     */
    private static final Pattern userPasswordPattern = Pattern.compile("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");

    /**
     * validates the user input.
     * @param userRegistrationRequest
     */
    public void validateUserInput(UserRegistrationRequest userRegistrationRequest) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();

        if (!isValidateDate(userRegistrationRequest.getDateOfBirth())) {
            userRegistrationResponse.getErrors().put("UserDateOfBirth", "Invalid Date of Birth format. Please specify date of birth in ISO-8601 format.");
        }
        if ( !isValidUserName(userRegistrationRequest.getUserName())) {
            userRegistrationResponse.getErrors().put("UserName" , "Invalid User Name. User Name must be alphanumeric and should not contain spaces and special characters");
        }
        if (!isValidPassword(userRegistrationRequest.getPassword())) {
            userRegistrationResponse.getErrors().put("Password", "Password should be atleast 4 characters long and should contain atleast 1 upper case letter, 1 number and shouldn't contain any special characters or spaces");
        }
        if (!userRegistrationResponse.getErrors().isEmpty()) {
            throw new InvalidUserInputException(userRegistrationResponse);
        }
    }

    /**
     * validates if the username is alphanumeric
     * @param userName
     * @return true if alphanumeric
     */
    private boolean isValidUserName(String userName) {
        return userNamePattern.matcher(userName).matches();
    }

    /**
     * validates if the password matches the given regex
     * @param password
     * @return true if matches the password regex
     */
    private boolean isValidPassword(String password) {
        return userPasswordPattern.matcher(password).matches();
    }

    /**
     * validates if the date of birth is in ISO-8601 format.
     * @param dateOfBirth
     * @return true if the date of birth is in ISO-8601 format.
     */
    private boolean isValidateDate(String dateOfBirth) {
        try {
            LocalDate.parse(dateOfBirth);
            return true;
        } catch (DateTimeParseException dtpException) {
            return false;
        }
    }
}
