package com.nishant.test.service.impl;

import com.nishant.test.service.UserRegisterService;
import com.nishant.test.dao.UserRegisterDao;
import com.nishant.test.entity.UserRegister;
import com.nishant.test.request.RegisterUserRequest;
import com.nishant.test.response.RegisterUserResponse;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterServiceImpl.class);

    private UserRegisterDao userRegisterDao;

    public UserRegisterServiceImpl(UserRegisterDao userRegisterDao) {
        this.userRegisterDao = userRegisterDao;
    }

    @Override
    public Optional<RegisterUserResponse> registerUser(RegisterUserRequest userRequest) {
        RegisterUserResponse userResponse = new RegisterUserResponse();
        try {
            UserRegister userRegister = new UserRegister();
            userRegister.setUsername(userRequest.getUsername());
            userRegister.setEmail(userRequest.getEmail());
            userRegister.setPassword(userRequest.getPassword());
            Long id = userRegisterDao.saveUser(userRegister);
            userResponse.setId(id);
        } catch (Exception e) {
            String cause = ExceptionUtils.getFullStackTrace(e);
            LOGGER.error("Inside UserRegisterServiceImpl.registerUser ---> userRequest {}, cause {}", userRequest, cause);
            userResponse.setError(cause);
        }
        return Optional.ofNullable(userResponse);
    }
}
