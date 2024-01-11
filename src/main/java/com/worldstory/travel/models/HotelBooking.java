package com.worldstory.travel.models;

import com.worldstory.travel.enums.BookingState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String address;

    private LocalDateTime createdDate;

    @Enumerated(EnumType.STRING)
    private BookingState state;

    @ManyToOne
    private Hotel hotel;
}
