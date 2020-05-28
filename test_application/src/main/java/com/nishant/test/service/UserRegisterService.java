package com.nishant.test.service;

import com.nishant.test.request.RegisterUserRequest;
import com.nishant.test.response.RegisterUserResponse;

import java.util.Optional;

public interface UserRegisterService {
    
    public Optional<RegisterUserResponse> registerUser(RegisterUserRequest userRequest);
}
