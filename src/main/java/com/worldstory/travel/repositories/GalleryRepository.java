package com.worldstory.travel.repositories;

import com.worldstory.travel.models.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Image, Integer> {
}
