package com.worldstory.travel.services;

import com.worldstory.travel.models.TourDetail;
import com.worldstory.travel.repositories.TourDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TourDetailService {

    @Autowired
    private TourDetailRepository tourDetailRepository;

    public TourDetail saveOrUpdate(TourDetail tourDetail) {
        return tourDetailRepository.save(tourDetail);
    }
}
