package com.worldstory.travel.services;

import com.worldstory.travel.models.Tour;
import com.worldstory.travel.repositories.TourRepository;
import com.worldstory.travel.utils.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourDetailService tourDetailService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private Pagination pagination;

    public Page<Tour> findAll() {
        Pageable pageable = pagination.page("1", "30");
        return tourRepository.findAll(pageable);
    }

    public Tour findById(String id) {
        try{
           int intId = Integer.parseInt(id);
           Tour tour = tourRepository.findById(intId).orElse(null);
           return tour;
        } catch (NumberFormatException err) {
            System.out.println(err);
            return null;
        }
    }

    @Transactional
    public Tour saveOrUpdate(Tour tour) {
        if(tour.getId() == null) {
            tour.setCreatedDate(LocalDateTime.now());
            tour.setIsActive(true);
        }
        tourDetailService.saveOrUpdate(tour.getTourDetail());
        addressService.saveAll(tour.getAddresses());
        tour.setUpdatedDate(LocalDateTime.now());
        return tourRepository.save(tour);
    }

    public void delete(Tour tour) {
        tourRepository.delete(tour);
    }
}
