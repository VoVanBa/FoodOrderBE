package com.example.foodApp.reponsitory;

import com.example.foodApp.model.IngredientCategory;
import com.example.foodApp.model.IngredientsItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientItemReponsitory extends JpaRepository<IngredientsItem,Long> {
    List<IngredientsItem> findByRestaurantId(Long id);
}
