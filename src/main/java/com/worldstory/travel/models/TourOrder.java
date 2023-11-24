package com.worldstory.travel.models;

import com.worldstory.travel.enums.TourOrderState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime departureDate;
    private int adultNumber;
    private int childNumber;
    private int babyNumber;

    @Enumerated(EnumType.STRING)
    private TourOrderState state;
}
