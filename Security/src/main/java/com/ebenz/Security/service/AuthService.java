package com.ebenz.Security.service;

import com.ebenz.Security.model.SignInRequest;
import com.ebenz.Security.model.SignUpRequest;

public interface AuthService {
    String authenticateUser(SignInRequest signInRequest);
    String createUser(SignUpRequest signUpRequest);
}
