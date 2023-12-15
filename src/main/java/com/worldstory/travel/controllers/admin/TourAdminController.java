package com.worldstory.travel.controllers.admin;

import com.worldstory.travel.models.Address;
import com.worldstory.travel.models.Tour;
import com.worldstory.travel.services.AmazonS3Service;
import com.worldstory.travel.services.TourBookingService;
import com.worldstory.travel.services.TourService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


public class TourAdminController {
    @Autowired
    private TourService tourService;

    @Autowired
    private TourBookingService tourBookingService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @GetMapping("")
    public String index(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("tours", tourService.findAll(params, false));
        return "pages/admin/tour";
    }

    @GetMapping("/add")
    public String addTour(Model model, @ModelAttribute(value = "tour") Tour tour) {
        if(tour.getDestinations() == null)
            tour.setDestinations(List.of(new Address()));
        if(tour.getDeparture() == null)
            tour.setDeparture(new Address());
        model.addAttribute("tour", tour);
        return "pages/admin/tour_add";
    }

    @PostMapping("/add")
    public String handleAddTour(Model model, @RequestParam(name = "file") MultipartFile file, @ModelAttribute(value = "tour") @Valid Tour tour, BindingResult bindingResult) {
        if(file.isEmpty() && tour.getImage().isEmpty())
            bindingResult.rejectValue("image", "form.error.empty");

        if (bindingResult.hasErrors()) {
            model.addAttribute("tour", tour);
            return "pages/admin/tour_add";
        }

        if(!file.isEmpty()) {
            if(!tour.getImage().isEmpty()) amazonS3Service.deleteFile(tour.getImage().split(".com/")[1]);
            tour.setImage(amazonS3Service.uploadFile(file));
        }

        tourService.saveOrUpdate(tour);
        return "redirect:/admin/tour";
    }

    @PostMapping("/add-address")
    public String addAddress(RedirectAttributes redirectAttributes, @ModelAttribute(value = "tour") Tour tour) {

        tour.getDestinations().add(new Address());
        redirectAttributes.addFlashAttribute("tour", tour);
        return "redirect:/admin/tour/add";
    }

    @GetMapping("/update/{id}")
    public String handleUpdateTour(Model model, @PathVariable(value = "id") String id) {
        Tour tour = tourService.findById(id);
        model.addAttribute("tour", tour);

        return "pages/admin/tour_add";
    }

    @PostMapping("/delete/{id}")
    public String deleteTour(@PathVariable(value = "id") String id) {
        Tour tour = tourService.findById(id);

        tourBookingService.deleteAll(tourBookingService.findByTourId(tour.getId()));
        tourService.delete(tour);

        return "redirect:/admin/tour";
    }


}
