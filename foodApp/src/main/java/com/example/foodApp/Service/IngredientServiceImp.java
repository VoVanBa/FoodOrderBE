package com.example.foodApp.Service;

import com.example.foodApp.model.IngredientCategory;
import com.example.foodApp.model.IngredientsItem;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.reponsitory.IngredientCategoryReponsitory;
import com.example.foodApp.reponsitory.IngredientItemReponsitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientServiceImp implements IngredientsService{
    @Autowired
    private IngredientCategoryReponsitory ingredientCategoryReponsitory;
    @Autowired
    private IngredientItemReponsitory ingredientItemReponsitory;
    @Autowired
    private RestaurantService restaurantService;
    @Override
    public IngredientCategory createIngredientCategory(String name, Long restaurantId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);

        IngredientCategory ingredientCategory=new IngredientCategory();
        ingredientCategory.setName(name);
        ingredientCategory.setRestaurant(restaurant);
        return ingredientCategoryReponsitory.save(ingredientCategory);
    }

    @Override
    public IngredientCategory findIngredientCategoryById(Long id) throws Exception {
        Optional<IngredientCategory> ingredientCategory = ingredientCategoryReponsitory.findById(id);
        if(ingredientCategory.isEmpty()){
            throw new Exception("ingredient category not found");
        }
        return ingredientCategory.get();
    }

    @Override
    public List<IngredientCategory> findIngredientCategoryByRestaurantId(Long id) throws Exception {
        restaurantService.findRestaurantById(id);
        return ingredientCategoryReponsitory.findByRestaurantId(id);
    }

    @Override
    public List<IngredientsItem> findRestaurantsIngredients(Long restaurantId) {
        return ingredientItemReponsitory.findByRestaurantId(restaurantId);
    }

    @Override
    public IngredientsItem createIngredientItem(Long restaurantId, String ingredientName, Long categoryId) throws Exception {
        Restaurant restaurant = restaurantService.findRestaurantById(restaurantId);
        IngredientCategory category=findIngredientCategoryById(categoryId);
        IngredientsItem ingredientsItem= new IngredientsItem();
        ingredientsItem.setName(ingredientName);
        ingredientsItem.setRestaurant(restaurant);
        ingredientsItem.setCategory(category);
        IngredientsItem ingredient=ingredientItemReponsitory.save(ingredientsItem);
        category.getIngredientsItems().add(ingredient);
        return ingredient;
    }

    @Override
    public IngredientsItem updateStock(Long id) throws Exception {
        Optional<IngredientsItem> optionalIngredientsItem=ingredientItemReponsitory.findById(id);
        if(optionalIngredientsItem.isEmpty()){
            throw new Exception("Ingredient not found");
        }
        IngredientsItem ingredientsItem=optionalIngredientsItem.get();
        ingredientsItem.setStoke(!ingredientsItem.isStoke());
        return ingredientItemReponsitory.save(ingredientsItem);
    }
}
