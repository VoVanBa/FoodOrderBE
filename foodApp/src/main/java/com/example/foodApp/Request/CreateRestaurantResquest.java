package com.example.foodApp.Request;

import com.example.foodApp.model.Address;
import com.example.foodApp.model.ContactInformation;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateRestaurantResquest {
    private Long id;
    private String name;
    private String description;
    private String mobile;
    private String cuisineType;
    private Address address;
    private ContactInformation contactInformation;
    private String openingHours;
    private List<String> images;
}
