package com.worldstory.travel.controllers;

import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.services.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("")
    public String index(Model model, @RequestParam Map<String, String> params) {
        Page<Hotel> hotels = hotelService.findAll(params, true);
        model.addAttribute("hotels", hotels);
        if(params.get("view") != null && params.get("view").equals("grid")) return "pages/hotels_grid";
        return "pages/hotels_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable(value = "id") String id) {
        Hotel hotel = hotelService.findById(id);

        model.addAttribute("hotel", hotel);

        return "pages/hotel_detail";
    }
}
