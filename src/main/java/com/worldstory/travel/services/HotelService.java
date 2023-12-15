package com.worldstory.travel.services;

import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.models.Tour;
import com.worldstory.travel.repositories.HotelRepository;
import com.worldstory.travel.specifications.HotelSpecification;
import com.worldstory.travel.utils.Pagination;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional

public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelSpecification hotelSpecification;

    @Autowired
    private AddressService addressService;

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    public Hotel findById(Integer id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public Hotel findById(String id) {
        try {
            Integer intId = Integer.parseInt(id);
            return hotelRepository.findById(intId).orElse(null);
        } catch (NumberFormatException numberFormatException) {
            return null;
        }
    }

    public Page<Hotel> findAll(Map<String, String> params, boolean isOnlyAllowActive) {
        if(params == null) params = new HashMap<>();
        if(isOnlyAllowActive) params.put("isActive", "true");

        Pagination<Hotel> pagination = new Pagination<>(params);

        List<Specification<Hotel>> specifications = pagination.getSpecifications();
        Pageable pageable = pagination.page(params.get("page"), params.get("limit"));

        if(params.get("starRate") != null)
            specifications.add(hotelSpecification.findByStarRate(Integer.parseInt(params.get("starRate"))));

        Page<Hotel> hotels = hotelRepository.findAll(specifications.stream().reduce(Specification.where(null), Specification::and), pageable);
        return hotels;
    }

    public Hotel save(Hotel hotel) {
        addressService.save(hotel.getAddress());
        galleryService.saveAll(hotel.getGallery());
        return hotelRepository.save(hotel);
    }

    public void delete(Hotel hotel) {
        galleryService.deleteAllInHotel(hotel);
        amazonS3Service.deleteFile(hotel.getImage().split(".com/")[1]);
        hotelRepository.delete(hotel);
    }
}
