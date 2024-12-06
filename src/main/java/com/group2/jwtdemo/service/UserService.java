package com.group2.jwtdemo.service;

import com.group2.jwtdemo.dto.request.UserRequest;
import com.group2.jwtdemo.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserRequest userRequest);

    UserResponse getUser(String username);

    List<UserResponse> getAllUser();


    UserResponse updateUser(Long id, UserRequest userRequest);

    String deleteUser(Long id);

    UserResponse getUserById(Long id);
}
