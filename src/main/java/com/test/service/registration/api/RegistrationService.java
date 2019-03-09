package com.test.service.registration.api;

import com.test.service.domain.User;
import com.test.service.exception.InvalidUserInputException;
import com.test.service.exception.UserAlreadyRegisteredException;
import com.test.service.exception.UserBlacklistedException;
import com.test.service.exclusion.ExclusionService;
import com.test.service.validation.UserInputValidator;
import com.test.service.repository.UserRepository;
import com.test.service.user.request.UserRegistrationRequest;
import com.test.service.user.response.UserRegistrationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Validate and register the user.
 */
@RestController
@RequestMapping("/user/registration")
public class RegistrationService {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationService.class);

    /**
     * registration user input validation resource
     */
    @Resource
    private UserInputValidator userInputValidator;

    /**
     * repository to register the user.
     */
    @Resource
    private UserRepository userRepository;

    /**
     * boolean to consider only userId for registration validation.
     */
    @Value("${user.registration.validation.userIdOnly}")
    private Boolean verifyOnlyUserId;

    /**
     * exclusion service to validate the blacklisted user.
     */
    @Resource
    private ExclusionService userExclusionService;

    /**
     * register api which validates and registers the user to repository.
     * Exceptions are thrown when registration fails. Exceptions are self-explanatory.
     *
     * @param userRegistrationRequest contains the user registration required input.
     * @return on successful registration a success response will be returned.*
     * @throws InvalidUserInputException when user input validation is failed
     * @throws UserAlreadyRegisteredException when user is already registered.
     * @throws UserBlacklistedException when user is blacklisted.
     */
    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> register(@RequestBody UserRegistrationRequest userRegistrationRequest)
            throws InvalidUserInputException, UserAlreadyRegisteredException, UserBlacklistedException {
        logger.info("User Registration details received.");

        logger.debug("Validating the data...");
        userInputValidator.validateUserInput(userRegistrationRequest);
        logger.debug("User validation successful.");

        if (!userExclusionService.validate(userRegistrationRequest.getDateOfBirth(), userRegistrationRequest.getSsn())) {
            throw new UserBlacklistedException("User:" + userRegistrationRequest.getUserName() + " has been blacklisted. Can't proceed with the registration.");
        }
        logger.debug("User is not blacklisted");
        isUserAlreadyRegistered(userRegistrationRequest);
        logger.debug("User is being registered...");
        userRepository.save(new User(userRegistrationRequest.getUserName(), BCrypt.hashpw(userRegistrationRequest.getPassword(), BCrypt.gensalt()),
                LocalDate.parse(userRegistrationRequest.getDateOfBirth()), userRegistrationRequest.getSsn()));
        logger.info("User has been successfully registered...");
        return new ResponseEntity<>(createSuccessResponse(userRegistrationRequest.getUserName()), HttpStatus.CREATED);
    }

    /**
     * create {@link UserRegistrationResponse} on successful registration.
     *
     * @param userName
     * @return
     */
    private UserRegistrationResponse createSuccessResponse(String userName) {
        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setHttpStatus(HttpStatus.CREATED);
        userRegistrationResponse.setMessage("User:"+ userName+ " has been successfully registered.");
        return userRegistrationResponse;
    }

    /**
     * Validates if the user is already present.
     * If present
     *      when {@code verifyOnlyUserId} is true --> throws UserAlreadyRegisteredException
     *      when {@code verifyOnlyUserId} is false, verifies date of birth and ssn of the user. --> throws UserAlreadyRegisteredException if all matchees.
     * @param userRegistrationRequest
     * @throws UserAlreadyRegisteredException
     */
    private void isUserAlreadyRegistered(UserRegistrationRequest userRegistrationRequest) throws UserAlreadyRegisteredException {
        Optional<User> user = userRepository.findByUserName(userRegistrationRequest.getUserName());
        if (user.isPresent()) {
            if (verifyOnlyUserId) {
                throw new UserAlreadyRegisteredException("User:" + userRegistrationRequest.getUserName()+ " has been already registered.");
            } else if(user.get().getDateOfBirth().isEqual(LocalDate.parse(userRegistrationRequest.getDateOfBirth()))
                    && user.get().getSsn().equals(userRegistrationRequest.getSsn())) {
                throw new UserAlreadyRegisteredException("User:" + userRegistrationRequest.getUserName()+ " has been already registered.");
            }
        }
    }

}
