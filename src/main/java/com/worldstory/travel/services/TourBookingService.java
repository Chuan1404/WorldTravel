package com.worldstory.travel.services;

import com.worldstory.travel.models.TourBooking;
import com.worldstory.travel.repositories.TourBookingRepository;
import com.worldstory.travel.specifications.TourBookingSpecification;
import com.worldstory.travel.utils.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TourBookingService {
    @Autowired
    private TourBookingRepository tourBookingRepository;


    public Page<TourBooking> findAll(Map<String, String> params) {
        if(params == null) params = new HashMap<>();
        Pagination<TourBooking> pagination = new Pagination<>(params);
        Pageable page = pagination.page(params.get("page"), params.get("limit"));

        return tourBookingRepository.findAll(page);
    }

    public List<TourBooking> findByTourId(Integer id) {
        return tourBookingRepository.findByTourId(id);
    }

    public TourBooking saveOrUpdate(TourBooking tourBooking) {
        if(tourBooking.getId() == null) {

        }

        return tourBookingRepository.save(tourBooking);
    }

    public void deleteAll(List<TourBooking> orderList) {
        orderList.forEach(order -> order.setTour(null));
        tourBookingRepository.deleteAll(orderList);
    }
}
