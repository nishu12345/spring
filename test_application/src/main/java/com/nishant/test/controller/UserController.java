package com.nishant.test.controller;

import com.nishant.test.request.RegisterUserRequest;
import com.nishant.test.response.CommonResponse;
import com.nishant.test.response.RegisterUserResponse;
import com.nishant.test.service.UserRegisterService;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.HttpServletRequest;

import java.util.Optional;

import static com.nishant.test.common.Constants.UrlMapping.USER_REGISTER;

@RestController
@RefreshScope
@EnableSwagger2
public class UserController {

    private UserRegisterService userRegisterService;

    public UserController(UserRegisterService userRegisterService) {
        this.userRegisterService = userRegisterService;
    }

    @PostMapping(path = USER_REGISTER, consumes = "application/json", produces = "application/json")
    public CommonResponse registerUserData(HttpServletRequest request, @RequestBody RegisterUserRequest userRequest) {
        CommonResponse commonResponse = new CommonResponse();
        Optional<RegisterUserResponse> registerUserResponse = userRegisterService.registerUser(userRequest);
        registerUserResponse.ifPresentOrElse(response -> {
            commonResponse.setStatus(true);
            commonResponse.setDescription("User Registered Successfully");
        }, () -> {
            commonResponse.setStatus(false);
            commonResponse.setDescription("User Registeration Failed");
        });
        commonResponse.setResponse(registerUserResponse);
        return commonResponse;
    }
}
