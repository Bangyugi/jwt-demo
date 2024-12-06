package com.group2.jwtdemo.service.impl;

import com.group2.jwtdemo.dto.request.UserRequest;
import com.group2.jwtdemo.dto.response.RoleResponse;
import com.group2.jwtdemo.dto.response.UserResponse;
import com.group2.jwtdemo.entity.Role;
import com.group2.jwtdemo.entity.User;
import com.group2.jwtdemo.repository.RoleRepository;
import com.group2.jwtdemo.repository.UserRepository;
import com.group2.jwtdemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createUser(UserRequest userRequest){
        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUsername(userRequest.getUsername());
        Set<Role> roles = new HashSet<>();
        Role role = roleRepository.findById(2L).orElseThrow(()->new RuntimeException("Role not found"));
        roles.add(role);
        user.setRoles(roles);
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setUserId(user.getUserId());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        userResponse.setRoles(roles.stream().map(result->{
            RoleResponse response = new RoleResponse();
            response.setId(result.getRoleId());
            response.setName(result.getName());
            return  response;
        }).collect(Collectors.toSet()));
        return userResponse;
    }

    @Override
    public UserResponse getUser(String username){
        User user = userRepository.findByUsername(username).orElseThrow(()-> new RuntimeException("User not found"));
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }
    @Override
    public List<UserResponse> getAllUser(){
        List<User> users = userRepository.findAll();
        return users.stream().map(user->{
            UserResponse userResponse = new UserResponse();
            userResponse.setEmail(user.getEmail());
            userResponse.setPassword(user.getPassword());
            userResponse.setUsername(user.getUsername());
            return userResponse;
        }).collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setUsername(userRequest.getUsername());
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setUsername(user.getUsername());
        return userResponse;
    }

    @Override
    public String deleteUser(Long id){
        userRepository.deleteById(id);
        return "Deleted successfully";
    }

    @Override
    public UserResponse getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        System.out.println(user.getRoles());
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setUserId(user.getUserId());
        Set<RoleResponse> roles = new HashSet<>();
        for(Role x: user.getRoles()){
        RoleResponse roleResponse = new RoleResponse();
            roleResponse.setName(x.getName());
            roleResponse.setId(x.getRoleId());
            roles.add(roleResponse);
        }
        userResponse.setRoles(roles);
        return userResponse;
    }
}
