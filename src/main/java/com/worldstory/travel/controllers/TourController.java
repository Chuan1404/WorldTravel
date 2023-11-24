package com.worldstory.travel.controllers;

import com.worldstory.travel.models.Tour;
import com.worldstory.travel.services.TourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable(value = "id") String id) {
        Tour tour = tourService.findById(id);

        model.addAttribute("tour", tour);
        return "pages/tour_detail";
    }
}
