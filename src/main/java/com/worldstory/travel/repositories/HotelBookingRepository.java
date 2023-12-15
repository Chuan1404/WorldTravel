package com.worldstory.travel.repositories;

import com.worldstory.travel.models.HotelBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelBookingRepository extends JpaRepository<HotelBooking, Integer> {
}
