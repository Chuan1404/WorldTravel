package com.worldstory.travel.services;

import com.worldstory.travel.models.Image;
import com.worldstory.travel.models.Hotel;
import com.worldstory.travel.repositories.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private AmazonS3Service amazonS3Service;

    @Autowired
    private HotelService hotelService;

    public Image findById(Integer id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public Image save(Image image) {
        return galleryRepository.save(image);
    }

    public Image save(String link) {
        Image image = new Image();
        image.setLink(link);
        return galleryRepository.save(image);
    }

    public List<Image> saveAll(List<Image> galleries) {
        return galleryRepository.saveAll(galleries);
    }

    public void delete(Integer id) {
        galleryRepository.deleteById(id);
    }

    public void delete(Image image) {
        galleryRepository.delete(image);
    }

    public void deleteOneInHotel(Integer hotelId, Integer galleryId) {
        Hotel hotel = hotelService.findById(hotelId);
        Image image = findById(galleryId);

        hotel.setGallery(hotel.getGallery().stream().filter(item -> !item.getId().equals(galleryId)).collect(Collectors.toList()));
        hotelService.save(hotel);

        if(image.getLink() != null)
            amazonS3Service.deleteFile(image.getLink().split(".com/")[1]);
        galleryRepository.delete(image);


    }

    public void deleteAllInHotel(Hotel hotel) {
        List<Image> galleries = hotel.getGallery();
        galleries.stream().forEach(item -> {
            if(item.getLink() != null)
                amazonS3Service.deleteFile(item.getLink().split(".com/")[1]);
        });

        hotel.setGallery(new ArrayList<>());
        hotelService.save(hotel);

        galleryRepository.deleteAll(galleries);
    }
}
