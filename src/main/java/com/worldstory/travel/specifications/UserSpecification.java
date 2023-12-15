package com.worldstory.travel.specifications;

import com.worldstory.travel.enums.UserRole;
import com.worldstory.travel.models.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class UserSpecification extends ModelSpecification<User> {
    public Specification<User> findByRole(UserRole userRole) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("roles").get("name"), userRole));
    }
}
