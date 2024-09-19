package com.example.foodApp.Service;

import com.example.foodApp.model.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(String name,Long userId) throws Exception;
    List<Category> findCategoryByRestaurantId(Long id) throws Exception;
    Category findCategoryById(Long id) throws Exception;
    Category updateCategory(String name,Long userId,Long categoryId) throws Exception;

}
