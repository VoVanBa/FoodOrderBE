package com.example.foodApp.controller;

import com.example.foodApp.Request.CreateFoodRequest;
import com.example.foodApp.Service.FoodService;
import com.example.foodApp.Service.RestaurantService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.Food;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.model.User;
import com.example.foodApp.response.MessageResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/food")
public class AdminFoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping
    public ResponseEntity<?> createFood(@RequestBody CreateFoodRequest req,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.getRestaurantByUserId(user.getId());

        Food food = foodService.createFooḍ(req,req.getCategory(),restaurant);
        return new ResponseEntity<>(food,HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFood(@PathVariable Long id,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        foodService.deleteFood(id);
        MessageResponse messageResponse= new MessageResponse();
        messageResponse.setMessage("food delete Success");
        return new ResponseEntity<>(messageResponse,HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFoodAvabityStatus(@PathVariable Long id,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        Food food = foodService.updateAvalibilityStatus(id);
        return new ResponseEntity<>(food,HttpStatus.OK);

    }
}
