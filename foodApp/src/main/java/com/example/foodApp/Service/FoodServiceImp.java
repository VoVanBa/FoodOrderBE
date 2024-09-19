package com.example.foodApp.Service;

import com.example.foodApp.Request.CreateFoodRequest;
import com.example.foodApp.model.Category;
import com.example.foodApp.model.Food;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.reponsitory.FoodResponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FoodServiceImp implements FoodService{
    @Autowired
    private FoodResponsitory foodResponsitory;
    @Override
    public Food createFooḍ(CreateFoodRequest req, Category category, Restaurant restaurant) {
        Food food= new Food();
        food.setFoodCategory(category);
        food.setRestaurant(restaurant);
        food.setDescription(req.getDescription());
        food.setImages(req.getImages());
        food.setName(req.getName());
        food.setSeasonal(req.isSeasional());
        food.setVegertarian(req.isVegetarian());
        food.setPrice(req.getPrice());
        food.setIngredients(req.getIngredients());
        food.setCreationDate(new Date());
        Food saveFood=foodResponsitory.save(food);
        restaurant.getFoods().add(saveFood);
        return saveFood;
    }

    @Override
    public void deleteFood(Long foodId) throws Exception {
        Food food= findFoodById(foodId);
        food.setRestaurant(null);
        foodResponsitory.delete(food);
    }

    @Override
    public List<Food> getRestaurantsFood(Long restaurantId, boolean isVegetarain, boolean isNonveg, boolean isSeasonal, String foodCategory) {
        List<Food> foods= foodResponsitory.findByRestaurantId(restaurantId);

        if(isVegetarain){
            foods= fillterByVegittarian(foods,isVegetarain);
        }

        if(isNonveg){
            foods= fillterByNonveg(foods,isNonveg);
        }

        if(isSeasonal){
            foods= fillterBySeasonal(foods,isSeasonal);
        }

        if(foodCategory != null && !foodCategory.equals("")){
            foods=fillterByCategory(foods,foodCategory);
        }
        return foods;
    }

    private List<Food> fillterByVegittarian(List<Food> foods, boolean isVegetarain){
        return foods.stream().filter(food -> food.isVegertarian()==isVegetarain).collect(Collectors.toList());
    }
    private List<Food> fillterByNonveg(List<Food> foods, boolean isNonveg){
        return foods.stream().filter(food -> !food.isVegertarian()).collect(Collectors.toList());

    }
    private List<Food> fillterBySeasonal(List<Food> foods, boolean isSeasonal){
        return foods.stream().filter(food -> food.isSeasonal()==isSeasonal).collect(Collectors.toList());

    }
    private List<Food> fillterByCategory(List<Food> foods, String foodCategory){
        return foods.stream().filter(food -> {
            if(food.getFoodCategory()!=null){
                return food.getFoodCategory().getName().equals(foodCategory);

            }
            return false;
        }).collect(Collectors.toList());
    }
    @Override
    public List<Food> searchFood(String keyword) {
        return foodResponsitory.searchFood(keyword);
    }

    @Override
    public Food findFoodById(Long foodId) throws Exception {
        Optional<Food> optionalFood=foodResponsitory.findById(foodId);
        if(optionalFood.isEmpty()){
            throw new Exception("food not exits");
        }
        return optionalFood.get();
    }

    @Override
    public Food updateAvalibilityStatus(Long foodId) throws Exception {
        Food food= findFoodById(foodId);
        food.setAvailable(!food.isAvailable());

        return foodResponsitory.save(food);
    }
}
