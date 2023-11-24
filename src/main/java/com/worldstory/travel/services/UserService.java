package com.worldstory.travel.services;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.User;
import com.worldstory.travel.repositories.RoleRepository;
import com.worldstory.travel.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder encoder;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User saveOrUpdate(User user) {
        if(user.getId() == null) {
            user.setActive(true);
            user.setPhone(null);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setCreatedDate(LocalDateTime.now());
            user.setUpdatedDate(LocalDateTime.now());
            user.setRoles(Set.of(roleService.findByName(UserRole.CUSTOMER)));
        }

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->new UsernameNotFoundException("User is not found"));
        return user;
    }
}
