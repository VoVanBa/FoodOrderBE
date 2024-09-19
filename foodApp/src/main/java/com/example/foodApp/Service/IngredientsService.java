package com.example.foodApp.Service;

import com.example.foodApp.model.IngredientCategory;
import com.example.foodApp.model.IngredientsItem;

import java.util.List;

public interface IngredientsService {
    IngredientCategory createIngredientCategory(String name,Long restaurantId) throws Exception;
    IngredientCategory findIngredientCategoryById(Long id) throws Exception;
    List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception;
    List<IngredientsItem> findRestaurantsIngredients(Long restaurantId);
    IngredientsItem createIngredientItem(Long restaurantId,String ingredientName,Long categoryId) throws Exception;

    IngredientsItem updateStock(Long id) throws Exception;
}
