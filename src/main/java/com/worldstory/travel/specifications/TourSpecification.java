package com.worldstory.travel.specifications;

import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.models.Tour;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TourSpecification extends ModelSpecification<Tour> {

    public Specification<Tour> findByArea(String country) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("departure").get("country"), country));
    }

}
