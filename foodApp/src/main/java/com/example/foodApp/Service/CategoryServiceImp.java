package com.example.foodApp.Service;

import com.example.foodApp.model.Category;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.reponsitory.CategoryResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImp implements CategoryService{
    @Autowired
    private CategoryResponsitory categoryResponsitory;
    @Autowired
    private RestaurantService restaurantService;
    @Override
    public Category createCategory(String name, Long userId) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category category= new Category();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryResponsitory.save(category);
    }

    @Override
    public List<Category> findCategoryByRestaurantId(Long id) throws Exception {
        Restaurant restaurant=restaurantService.findRestaurantById(id);
        return categoryResponsitory.findByRestaurantId(id);
    }

    @Override
    public Category findCategoryById(Long id) throws Exception {
        Optional<Category> category= categoryResponsitory.findById(id);
        if(category.isEmpty()){
            throw new Exception("category not found");
        }
        return category.get();
    }

    @Override
    public Category updateCategory(String name, Long userId,Long categoryId) throws Exception {
        Restaurant restaurant=restaurantService.getRestaurantByUserId(userId);
        Category category= categoryResponsitory.findById(categoryId).get();
        category.setName(name);
        category.setRestaurant(restaurant);
        return categoryResponsitory.save(category);
    }
}
