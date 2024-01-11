package com.worldstory.travel.services;

import com.worldstory.travel.models.Tour;
import com.worldstory.travel.repositories.TourRepository;
import com.worldstory.travel.specifications.ModelSpecification;
import com.worldstory.travel.specifications.TourSpecification;
import com.worldstory.travel.utils.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TourService {
    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private TourDetailService tourDetailService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private TourSpecification tourSpecification;


    public Page<Tour> findAll(Map<String, String> params, boolean isOnlyAllowActive) {
        if(params == null) params = new HashMap<>();
        if(isOnlyAllowActive) params.put("isActive", "true");
        Pagination<Tour> pagination = new Pagination<>(params);

        boolean isDomestic = params.get("domestic") != null && params.get("domestic").equals("1");
        boolean isForeign = params.get("foreign") != null && params.get("foreign").equals("1");

        List<Specification<Tour>> specifications = pagination.getSpecifications();
        Pageable pageable = pagination.page(params.get("page"), params.get("limit"));

        if(isDomestic && !isForeign)
            specifications.add(tourSpecification.findByArea("VN"));
        else if (isForeign && !isDomestic)
            specifications.add(Specification.not(tourSpecification.findByArea("VN")));

        Page<Tour> tours = tourRepository.findAll(specifications.stream().reduce(Specification.where(null), Specification::and), pageable);
        return tours;
    }

    public Tour findById(String id) {
        try{
           int intId = Integer.parseInt(id);
           Tour tour = tourRepository.findById(intId).orElse(null);
           return tour;
        } catch (NumberFormatException err) {
            return null;
        }
    }

    public Tour saveOrUpdate(Tour tour) {
        if(tour.getId() == null) {
            tour.setCreatedDate(LocalDateTime.now());
            tour.setIsActive(true);
        }

        tourDetailService.saveOrUpdate(tour.getTourDetail());
        addressService.saveAll(tour.getDestinations());
        addressService.save(tour.getDeparture());

        tour.setUpdatedDate(LocalDateTime.now());
        return tourRepository.save(tour);
    }

    public void delete(Tour tour) {
        tourRepository.delete(tour);
    }
}
