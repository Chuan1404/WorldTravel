package com.worldstory.travel.controllers.admin;

import com.worldstory.travel.models.HotelBooking;
import com.worldstory.travel.models.TourBooking;
import com.worldstory.travel.services.HotelBookingService;
import com.worldstory.travel.services.TourBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/admin/booking")
public class BookingAdminController {

    @Autowired
    private HotelBookingService hotelBookingService;

    @Autowired
    private TourBookingService tourBookingService;

    @GetMapping("")
    public String index(Model model, @RequestParam Map<String, String> params) {
        if(params.get("type") == null) params.put("type", "tour");

        if(params.get("type").equals("hotel")) {
            Page<HotelBooking> hotelBookings = hotelBookingService.findAll(params);
            model.addAttribute("hotelBookings", hotelBookings);
            return "pages/admin/hotel_booking";
        }

        Page<TourBooking> tourBookings = tourBookingService.findAll(params);
        model.addAttribute("tourBookings", tourBookings);
        return "pages/admin/tour_booking";
    }

//    @GetMapping("/hotel")
//    public String bookHotel(Model model, @RequestParam Map<String, String> params) {
//        Page<HotelBooking> hotelBookings = hotelBookingService.findAll(params);
//        model.addAttribute("hotelBookings", hotelBookings);
//
//        return "pages/admin/booking";
//    }
}
