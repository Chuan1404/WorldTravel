package com.worldstory.travel.controllers;

import com.worldstory.travel.services.HotelService;
import com.worldstory.travel.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private TourService tourService;

    @Autowired
    private HotelService hotelService;

    @GetMapping("")
    public String index(Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "5");

        model.addAttribute("top5Tours", tourService.findAll(params, true));

        params.put("limit", "6");
        model.addAttribute("top6Hotels", hotelService.findAll(params, true));


        return "pages/index";
    }

    @GetMapping("thank")
    public String thanks() {
        return "pages/thanks";
    }
}

