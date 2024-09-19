package com.example.foodApp.controller;

import com.example.foodApp.Request.IngredientCategoryRequest;
import com.example.foodApp.Request.IngredientRequest;
import com.example.foodApp.Service.IngredientsService;
import com.example.foodApp.model.IngredientCategory;
import com.example.foodApp.model.IngredientsItem;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class IngredientController {
    @Autowired
    private IngredientsService ingredientsService;

    @PostMapping("/admin/ingredients/category")
    public ResponseEntity<?> createIngredientCategory(
            @RequestBody IngredientCategoryRequest req) throws Exception {
        IngredientCategory item= ingredientsService.createIngredientCategory(req.getName(), req.getRestaurantId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/admin/ingredients")
    public ResponseEntity<?> createIngredientItem(
            @RequestBody IngredientRequest req) throws Exception {
        IngredientsItem item= ingredientsService.createIngredientItem(req.getRestaurantId(),req.getName(), req.getCategoryId());
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping("/admin/ingredients/{id}/stoke")
    public ResponseEntity<?> updateIngredientStock(
            @PathVariable Long id) throws Exception {
        IngredientsItem item= ingredientsService.updateStock(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/ingredients/restaurant/{id}")
    public ResponseEntity<?> getRestaurantIngredient(
            @PathVariable Long id) throws Exception {
        List<IngredientsItem> item= ingredientsService.findRestaurantsIngredients(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @GetMapping("/ingredients/restaurant/{id}/category")
    public ResponseEntity<?> getRCategoryIngredient(
            @PathVariable Long id) throws Exception {
        List<IngredientCategory> item= ingredientsService.findIngredientCategoryByRestaurantId(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
}
