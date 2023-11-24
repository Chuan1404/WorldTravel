package com.worldstory.travel.controllers.admin;

import com.worldstory.travel.models.Address;
import com.worldstory.travel.models.Tour;
import com.worldstory.travel.services.AmazonS3Service;
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin/tour")
public class TourAdminController {
    @Autowired
    private TourService tourService;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("tours", tourService.findAll());
        return "pages/admin/tour";
    }

    @GetMapping("/add")
    public String addTour(Model model, @ModelAttribute(value = "tour") Tour tour) {
        if(tour.getAddresses() == null)
            tour.setAddresses(List.of(new Address()));
        model.addAttribute("tour", tour);
        return "pages/admin/tour_add";
    }

    @PostMapping("/add")
    @Transactional
    public String handleAddTour(Model model, @RequestParam(name = "file") MultipartFile file, @ModelAttribute(value = "tour") @Valid Tour tour, BindingResult bindingResult) {
        System.out.println(tour.getAddresses());

        if(file.isEmpty() && tour.getImage().isEmpty())
            bindingResult.rejectValue("image", "form.error.empty");
        if (bindingResult.hasErrors()) {
            model.addAttribute("tour", tour);
            return "pages/admin/tour_add";
        }

        if(!file.isEmpty()) {
            if(!tour.getImage().isEmpty()) amazonS3Service.deleteFile(tour.getImage().split(".com/")[1]);
            amazonS3Service.uploadFile(file);
            tour.setImage(amazonS3Service.uploadFile(file));
        }

        tourService.saveOrUpdate(tour);
        return "redirect:/admin/tour";
    }

    @PostMapping("/add-address")
    public String addAddress(RedirectAttributes redirectAttributes, @ModelAttribute(value = "tour") Tour tour) {

        tour.getAddresses().add(new Address());
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
        tourService.delete(tour);

        return "redirect:/admin/tour";
    }
}
