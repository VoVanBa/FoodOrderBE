package com.example.foodApp.Service;

import com.example.foodApp.Request.CreateRestaurantResquest;
import com.example.foodApp.dto.RestaurantDto;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface RestaurantService {
    Restaurant createRestauranṭ(CreateRestaurantResquest req, User user);
    Restaurant updateRestaurant(Long retaurantId,CreateRestaurantResquest updateRestaurant) throws Exception;
    void deleteRestaurant(Long restaurantId) throws Exception;
    Page<Restaurant> getAllRestaurant(PageRequest pageRequest);
    Page<Restaurant> searchRestaurant(PageRequest pageRequest,String keyword);
    Restaurant findRestaurantById(Long id) throws Exception;
    Restaurant getRestaurantByUserId(Long userId) throws Exception;
    RestaurantDto addFavorites(Long restaurantId, User user) throws Exception;
    Restaurant updateRestaurantStatus(Long id) throws Exception;
}
