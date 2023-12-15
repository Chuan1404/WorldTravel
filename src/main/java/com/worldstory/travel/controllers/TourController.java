package com.worldstory.travel.controllers;

import com.worldstory.travel.models.Tour;
import com.worldstory.travel.services.TourBookingService;
import com.worldstory.travel.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @Autowired
    private TourBookingService tourBookingService;

    @GetMapping("")
    public String index(Model model, @RequestParam Map<String, String> params) {

        Page<Tour> tours = tourService.findAll(params, true);
        model.addAttribute("tours", tours);
        if(params.get("view") != null && params.get("view").equals("grid")) return "pages/tours_grid";
        return "pages/tours_list";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable(value = "id") String id) {

        Tour tour = tourService.findById(id);

        model.addAttribute("tour", tour);

        return "pages/tour_detail";
    }

}
