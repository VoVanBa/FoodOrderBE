package com.example.foodApp.response;

import com.example.foodApp.model.Restaurant;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantListResponse {
    List<Restaurant> restaurant;
    int totalPage;
}
