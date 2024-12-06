package com.group2.jwtdemo.service.impl;

import com.group2.jwtdemo.dto.request.LoginRequest;
import com.group2.jwtdemo.dto.response.JwtAuthResponse;
import com.group2.jwtdemo.dto.response.RoleResponse;
import com.group2.jwtdemo.dto.response.UserResponse;
import com.group2.jwtdemo.entity.User;
import com.group2.jwtdemo.repository.UserRepository;
import com.group2.jwtdemo.secutity.JwtTokenProvider;
import com.group2.jwtdemo.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public JwtAuthResponse login(LoginRequest loginRequest){
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()->new RuntimeException("User not found"));
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),loginRequest.getPassword()

        ));

        String jwtToken = jwtTokenProvider.generateToken(user);
        String refreshToken = jwtTokenProvider.generateToken(user);

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(user.getUserId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(user.getRoles().stream().map(result->new RoleResponse(result.getRoleId(),result.getName())).collect(Collectors.toSet()));

        return JwtAuthResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .userResponse(userResponse)
                .build();



    }

}
