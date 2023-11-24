package com.worldstory.travel.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String departure;
    private String destination;
    private LocalDateTime startDate;

    @OneToOne
    private User advisor;

    @OneToMany
    private Set<User> customers;

    @ManyToMany
    @JoinTable(name = "trip_tourguide", joinColumns = @JoinColumn(name = "trip_id"), inverseJoinColumns = @JoinColumn(name = "tourguide_id"))
    private Set<User> tourGuides;
}
