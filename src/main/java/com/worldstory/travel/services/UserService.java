package com.worldstory.travel.services;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.User;
import com.worldstory.travel.repositories.UserRepository;
import com.worldstory.travel.specifications.UserSpecification;
import com.worldstory.travel.utils.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserSpecification userSpecification;

    @Autowired
    private PasswordEncoder encoder;

    public Page<User> findAll(Map<String, String> params, boolean isOnlyAllowActive) {
        if(params == null) params = new HashMap<>();
        if(isOnlyAllowActive) params.put("isActive", "true");
        if(params.get("role") == null) params.put("role", "CUSTOMER");
        Pagination<User> pagination = new Pagination<>(params);

        List<Specification<User>> specifications = pagination.getSpecifications();
        specifications.add(userSpecification.findByRole(UserRole.valueOf(params.get("role"))));


        Pageable pageable = pagination.page(params.get("page"), params.get("limit"));

        return userRepository.findAll(specifications.stream().reduce(Specification.where(null), Specification::and), pageable);
    }

    public User findById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User findByEmailAndPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);

        if(user != null && user.getPassword().equals(encoder.encode(password)))
            return user;

        return null;
    }

    public User saveOrUpdate(User user) {
        if(user.getId() == null || user.getId().isEmpty()) {
            user.setIsActive(true);
            user.setPassword(encoder.encode(user.getPassword()));
            user.setCreatedDate(LocalDateTime.now());
        }
        user.setUpdatedDate(LocalDateTime.now());
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() ->new UsernameNotFoundException("User is not found"));
        return user;
    }

    public void delete(User user) {
        userRepository.delete(user);
    }
}
