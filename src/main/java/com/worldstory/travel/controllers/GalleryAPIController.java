package com.worldstory.travel.controllers;

import com.worldstory.travel.services.GalleryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gallery")
@Transactional
public class GalleryAPIController {

    @Autowired
    private GalleryService galleryService;

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id, @RequestParam(value = "hotelId") Integer hotelId) {

        galleryService.deleteOneInHotel(hotelId, id);
        return ResponseEntity.ok().body("Success");
    }


}
