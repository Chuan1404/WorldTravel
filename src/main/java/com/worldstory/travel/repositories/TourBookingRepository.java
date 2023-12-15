package com.worldstory.travel.repositories;

import com.worldstory.travel.models.TourBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TourBookingRepository extends JpaRepository<TourBooking, Integer> {
    List<TourBooking> findByTourId(Integer id);
}
