package com.worldstory.travel.controllers;

import com.worldstory.travel.dtos.ErrorResponse;
import com.worldstory.travel.dtos.MessageResponse;
import com.worldstory.travel.enums.BookingState;
import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.models.HotelBooking;
import com.worldstory.travel.models.Tour;
import com.worldstory.travel.models.TourBooking;
import com.worldstory.travel.services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/order")
@Transactional
public class OrderAPIController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourBookingService tourBookingService;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelBookingService hotelBookingService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/tour")
    public ResponseEntity<?> orderTour(@RequestBody Map<String, String> map) {
        Tour tour = tourService.findById(map.get("tourId"));

        try {
            String[] date = map.get("departureDate").split("-");
            TourBooking tourBooking = new TourBooking();

            tourBooking.setTour(tour);
            tourBooking.setState(BookingState.UN_PROCESS);
            tourBooking.setEmail(map.get("email"));
            tourBooking.setName(map.get("name"));
            tourBooking.setPhone(map.get("phone"));
            tourBooking.setAdultNumber(Integer.parseInt(map.get("adultNumber")));
            tourBooking.setChildNumber(Integer.parseInt(map.get("childNumber")));
            tourBooking.setBabyNumber(Integer.parseInt(map.get("babyNumber")));
            tourBooking.setDepartureDate(new Date(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])));
            tourBooking.setDescription(map.get("description"));
            tourBooking.setCreatedDate(LocalDateTime.now());

            tourBooking = tourBookingService.saveOrUpdate(tourBooking);

            emailService.sendEmail(tourBooking);
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Hãy đảm bảo nhập đúng thông tin"));
        }
        return ResponseEntity.ok().body(new MessageResponse("Thành công"));
    }

    @PostMapping("/hotel")
    public ResponseEntity<?> orderHotel(@RequestBody Map<String, String> map) {
        Hotel hotel = hotelService.findById(map.get("hotelId"));

        try {
            HotelBooking hotelBooking = new HotelBooking();

            hotelBooking.setName(map.get("name"));
            hotelBooking.setPhone(map.get("phone"));
            hotelBooking.setEmail(map.get("email"));
            hotelBooking.setAddress(map.get("address"));
            hotelBooking.setState(BookingState.UN_PROCESS);
            hotelBooking.setCreatedDate(LocalDateTime.now());

            hotelBooking.setHotel(hotel);

            hotelBooking = hotelBookingService.saveOrUpdate(hotelBooking);

            emailService.sendEmail(hotelBooking);
        } catch (Exception err) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Hãy đảm bảo nhập đúng thông tin"));
        }
        return ResponseEntity.ok().body(new MessageResponse("Thành công"));
    }
}
