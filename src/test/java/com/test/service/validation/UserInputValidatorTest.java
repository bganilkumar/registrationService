package com.test.service.validation;

import com.test.service.exception.InvalidUserInputException;
import com.test.service.user.request.UserRegistrationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserInputValidatorTest {

    @InjectMocks
    private UserInputValidator userInputValidator = new UserInputValidator();

    private UserRegistrationRequest userRegistrationRequest;


    @Before
    public void before() {
        userRegistrationRequest  = new UserRegistrationRequest();
        userRegistrationRequest.setDateOfBirth("1985-06-07");
        userRegistrationRequest.setPassword("pass1Word");
        userRegistrationRequest.setSsn("anilSSN");
        userRegistrationRequest.setUserName("bganilkumar4");
    }

    @Test
    public void testAllValidUserInput() {
        userInputValidator.validateUserInput(userRegistrationRequest);
    }

    @Test(expected = InvalidUserInputException.class)
    public void testDoBInvalidInput() {
        userRegistrationRequest.setDateOfBirth("2018-200-354");
        userInputValidator.validateUserInput(userRegistrationRequest);
    }

    @Test(expected = InvalidUserInputException.class)
    public void testUserNameInvalidInput() {
        userRegistrationRequest.setUserName("bganilkumar45&&");
        userInputValidator.validateUserInput(userRegistrationRequest);
    }

    @Test(expected = InvalidUserInputException.class)
    public void testPasswordInvalidInput() {
        userRegistrationRequest.setPassword("pas");
        userInputValidator.validateUserInput(userRegistrationRequest);
    }

    @Test(expected = InvalidUserInputException.class)
    public void testPasswordWMoreThan4ButMissingNumberInput() {
        userRegistrationRequest.setPassword("pasSw");
        userInputValidator.validateUserInput(userRegistrationRequest);
    }

    @Test(expected = InvalidUserInputException.class)
    public void testPasswordWMoreThan4HavingSpecialCharsInput() {
        userRegistrationRequest.setPassword("pasSw1$");
        userInputValidator.validateUserInput(userRegistrationRequest);
    }
}
