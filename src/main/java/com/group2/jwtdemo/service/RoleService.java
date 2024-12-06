package com.group2.jwtdemo.service;

import com.group2.jwtdemo.dto.request.RoleRequest;
import com.group2.jwtdemo.dto.response.RoleResponse;
import com.group2.jwtdemo.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    // Create a new role


    // Create a new role
    RoleResponse createRole(RoleRequest roleRequest);

    // Retrieve a role by ID
    Optional<Role> getRoleById(Long roleId);

    // Retrieve all roles
    List<Role> getAllRoles();

    // Update a role
    Role updateRole(Long roleId, Role roleDetails);

    // Delete a role
    void deleteRole(Long roleId);
}
