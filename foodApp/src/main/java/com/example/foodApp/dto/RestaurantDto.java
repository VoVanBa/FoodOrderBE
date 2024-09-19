package com.example.foodApp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Embeddable
public class RestaurantDto {
    private String name;

    @Column(length = 1000)
    private List<String > images;

    private String description;
    private Boolean open;

    private Long id;
}
