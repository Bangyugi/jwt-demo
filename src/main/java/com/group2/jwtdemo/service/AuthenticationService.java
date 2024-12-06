package com.group2.jwtdemo.service;

import com.group2.jwtdemo.dto.request.LoginRequest;
import com.group2.jwtdemo.dto.response.JwtAuthResponse;

public interface AuthenticationService {
    JwtAuthResponse login(LoginRequest loginRequest);
}
