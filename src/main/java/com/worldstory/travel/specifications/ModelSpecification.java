package com.worldstory.travel.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ModelSpecification<T> {
    public Specification<T> findByKw(String kw) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + kw.toLowerCase() + "%");
    }

    public Specification<T> greaterThanOrEqualTo(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("price"), price);
    }

    public Specification<T> lessThanOrEqualTo(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
    }

    public Specification<T> findActive(boolean isActive) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isActive"), isActive));
    }
}
