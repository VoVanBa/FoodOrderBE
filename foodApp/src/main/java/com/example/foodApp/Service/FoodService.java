package com.example.foodApp.Service;

import com.example.foodApp.Request.CreateFoodRequest;
import com.example.foodApp.model.Category;
import com.example.foodApp.model.Food;
import com.example.foodApp.model.Restaurant;

import java.util.List;

public interface FoodService {
     Food createFooḍ(CreateFoodRequest req, Category category, Restaurant restaurant);
    void deleteFood(Long foodId) throws Exception;
    List<Food> getRestaurantsFood(Long restaurantId,boolean isVegetarain,boolean isNonveg,boolean isSeasonal,String foodCategory);
    List<Food> searchFood(String keyword);
    Food findFoodById(Long foodId) throws Exception;
    Food updateAvalibilityStatus(Long foodId) throws Exception;

}
