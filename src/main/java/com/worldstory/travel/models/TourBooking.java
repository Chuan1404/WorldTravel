package com.worldstory.travel.models;

import com.worldstory.travel.enums.BookingState;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TourBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty(message = "{form.error.empty}")
    private String name;

    @NotEmpty(message = "{form.error.empty}")
    @NotNull
    private String email;

    @NotEmpty(message = "{form.error.empty}")
    @NotNull
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date departureDate;

    private String description;

    private int adultNumber;
    private int childNumber;
    private int babyNumber;

    @Enumerated(EnumType.STRING)
    private BookingState state;

    @ManyToOne(fetch = FetchType.EAGER)
    private Tour tour;

    private LocalDateTime createdDate;
}
