package com.worldstory.travel.services;

import com.worldstory.travel.models.HotelBooking;
import com.worldstory.travel.models.TourBooking;
import com.worldstory.travel.repositories.HotelBookingRepository;
import com.worldstory.travel.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class HotelBookingService {
    @Autowired
    private HotelBookingRepository hotelBookingRepository;

    public Page<HotelBooking> findAll(Map<String, String> params) {
        if(params == null) params = new HashMap<>();
        Pagination<HotelBooking> pagination = new Pagination<>(params);
        Pageable page = pagination.page(params.get("page"), params.get("limit"));

        return hotelBookingRepository.findAll(page);
    }

    public HotelBooking saveOrUpdate(HotelBooking hotelBooking) {
        return  hotelBookingRepository.save(hotelBooking);
    }
}
