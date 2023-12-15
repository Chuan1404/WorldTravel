package com.worldstory.travel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @Min(value = 1)
    private int dayDuration;

    @Min(value = 1)
    private int nightDuration;

    @Min(value = 1000)
    private double price;

    private String image;

    private Boolean isActive;

    @NotNull
    @NotEmpty
    private String vehicle;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime updatedDate;

    @OneToOne(fetch = FetchType.EAGER)
    private TourDetail tourDetail;

    @OneToMany
    private List<Trip> trips;

    @OneToOne
    private Address departure;

    @OneToMany
    private List<Address> destinations;
}
