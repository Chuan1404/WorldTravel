package com.worldstory.travel.specifications;

import com.worldstory.travel.models.Hotel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class HotelSpecification extends ModelSpecification<Hotel> {
    public Specification<Hotel> findByStarRate(int rate) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("starRate"), rate);
    }
}
