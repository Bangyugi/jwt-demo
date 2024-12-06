package com.group2.jwtdemo.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    private Long userId;
    private String username;
    private String password;
    private String email;
    Set<RoleResponse> roles = new HashSet<>();
}
