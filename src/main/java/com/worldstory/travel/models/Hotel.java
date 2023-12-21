package com.worldstory.travel.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotEmpty
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Min(value = 0)
    private Double price;

    private Integer starRate;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private String addressDetail;

    private String image;

    private Boolean isActive;

    @OneToMany
    private List<Image> gallery;

    @OneToOne
    private Address address;
}
