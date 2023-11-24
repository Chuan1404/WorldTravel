package com.worldstory.travel.controllers;

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
public class Home {

    @Autowired
    private TourService tourService;



    @GetMapping("")
    public String index(Model model) {
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("limit", "5");

        System.out.println(tourService.findAll(params));
        model.addAttribute("top5Tours", tourService.findAll(params));


        return "pages/index";
    }
}

