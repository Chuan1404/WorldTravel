package com.worldstory.travel.repositories;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(UserRole role);
}
