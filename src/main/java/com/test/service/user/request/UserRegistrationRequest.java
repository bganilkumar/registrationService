package com.test.service.user.request;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * user registration request details
 */
public class UserRegistrationRequest {

    @Setter
    @Getter @NonNull
    private String userName;
    @Setter @Getter @NonNull
    private String dateOfBirth;
    @Setter @Getter @NonNull
    private String password;
    @Setter @Getter @NonNull
    private String ssn;
}
