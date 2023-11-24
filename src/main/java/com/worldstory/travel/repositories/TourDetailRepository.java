package com.worldstory.travel.repositories;

import com.worldstory.travel.models.TourDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TourDetailRepository extends JpaRepository<TourDetail, Integer> {
}
