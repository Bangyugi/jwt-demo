package com.group2.jwtdemo.service.impl;

import com.group2.jwtdemo.dto.request.RoleRequest;
import com.group2.jwtdemo.dto.response.RoleResponse;
import com.group2.jwtdemo.entity.Role;
import com.group2.jwtdemo.repository.RoleRepository;
import com.group2.jwtdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    // Create a new role
    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = new Role();
        role.setName(roleRequest.getName());
         roleRepository.save(role);
         return RoleResponse.builder()
                 .id(role.getRoleId())
                 .name(role.getName())
                 .build();
    }

    // Retrieve a role by ID
    @Override
    public Optional<Role> getRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    // Retrieve all roles
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Update a role
    @Override
    public Role updateRole(Long roleId, Role roleDetails) {
        return roleRepository.findById(roleId).map(role -> {
            role.setName(roleDetails.getName());
            return roleRepository.save(role);
        }).orElseThrow(() -> new RuntimeException("Role not found with id " + roleId));
    }

    // Delete a role
    @Override
    public void deleteRole(Long roleId) {
        roleRepository.deleteById(roleId);
    }
}

