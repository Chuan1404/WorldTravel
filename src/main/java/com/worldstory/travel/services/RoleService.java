package com.worldstory.travel.services;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.Role;
import com.worldstory.travel.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role findByName(UserRole role) {
        return roleRepository.findByName(role).orElse(null);
    }
}
