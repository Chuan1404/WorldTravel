package com.worldstory.travel.controllers.admin;

import com.worldstory.travel.models.Address;
import com.worldstory.travel.models.Image;
import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.services.AmazonS3Service;
import com.worldstory.travel.services.GalleryService;
import com.worldstory.travel.services.HotelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/hotel")
public class HotelAdminController {

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private GalleryService galleryService;

    @GetMapping("")
    public String index(Model model, @RequestParam Map<String, String> params) {
        Page<Hotel> hotels = hotelService.findAll(params, false);
        model.addAttribute("hotels", hotels);
        return "pages/admin/hotel";
    }

    @GetMapping("/add")
    public String addHotel(Model model, @ModelAttribute(value = "hotel") Hotel hotel) {
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setAddress(new Address());
        }

        model.addAttribute("hotel", hotel);
        return "pages/admin/hotel_add";
    }

    @PostMapping("/add")
    @Transactional
    public String handleAddHotel(Model model, @ModelAttribute(value = "hotel") @Valid Hotel hotel,
                                 BindingResult bindingResult,
                                 @RequestParam(value = "file") MultipartFile file,
                                 @RequestParam(value = "multiFiles") List<MultipartFile> multipartFiles) {
        if (file.isEmpty() && hotel.getImage().isEmpty())
            bindingResult.rejectValue("image", "form.error.empty");

        if (bindingResult.hasErrors()) {
            model.addAttribute("hotel", hotel);
            return "pages/admin/hotel_add";
        }

        if (!file.isEmpty()) {
            if (!hotel.getImage().isEmpty()) amazonS3Service.deleteFile(hotel.getImage().split(".com/")[1]);
            hotel.setImage(amazonS3Service.uploadFile(file));
        }

        if (multipartFiles.size() > 0) {
            multipartFiles.forEach(image -> {
                        String url = amazonS3Service.uploadFile(image);
                        if(url != null) {
                            Image img = galleryService.save(url);
                            hotel.getGallery().add(img);
                        }

                    }
            );
        }

        hotelService.save(hotel);

        return "redirect:/admin/hotel";
    }

    @GetMapping("/update/{id}")
    public String updateHotel(Model model, @PathVariable(value = "id") String id) {
        Hotel hotel = hotelService.findById(id);
        model.addAttribute("hotel", hotel);
        return "pages/admin/hotel_add";
    }

    @PostMapping("/delete/{id}")
    public String deleteHotel(@PathVariable(value = "id") String id) {
        Hotel hotel = hotelService.findById(id);
        hotelService.delete(hotel);
        return "redirect:/admin/hotel";
    }
}
