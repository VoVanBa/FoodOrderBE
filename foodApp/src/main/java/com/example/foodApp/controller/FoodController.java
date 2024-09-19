package com.example.foodApp.controller;

import com.example.foodApp.Request.CreateFoodRequest;
import com.example.foodApp.Service.FoodService;
import com.example.foodApp.Service.RestaurantService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.Food;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.model.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
public class FoodController {
    @Autowired
    private FoodService foodService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/search")
    public ResponseEntity<?> searchFood(@RequestParam String name,
                                        @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);

        List<Food> foods = foodService.searchFood(name);
        return new ResponseEntity<>(foods, HttpStatus.CREATED);
    }
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<?> searchFood(@RequestParam(required = false) boolean vegetarian,
                                        @RequestParam(required = false) boolean seassonal,
                                        @RequestParam(required = false) boolean nonveg,
                                        @RequestParam(required = false) String food_category,
                                        @PathVariable Long restaurantId,
                                        @RequestParam(defaultValue = "1") int page ,
                                        @RequestParam(defaultValue = "8") int limit
                                    //    @RequestHeader("Authorization") String jwt
    ) throws Exception {
        PageRequest request=PageRequest.of(page-1,limit);
       // User user = userService.findUserByJwtToken(jwt);
        //lấy toonge số trang
        Page<Restaurant> restaurant= restaurantService.getAllRestaurant(request);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId,vegetarian,nonveg,seassonal,food_category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }

    @GetMapping("/admin/restaurant/{restaurantId}")
    public ResponseEntity<?> searchAdminFood(@RequestParam(required = false) boolean vegetarian,
                                        @RequestParam(required = false) boolean seassonal,
                                        @RequestParam(required = false) boolean nonveg,
                                        @RequestParam(required = false) String food_category,
                                        @PathVariable Long restaurantId,
                                        @RequestParam(defaultValue = "1") int page ,
                                        @RequestParam(defaultValue = "8") int limit,
                                           @RequestHeader("Authorization") String jwt
    ) throws Exception {
        PageRequest request=PageRequest.of(page-1,limit);
         User user = userService.findUserByJwtToken(jwt);
        //lấy toonge số trang
        Page<Restaurant> restaurant= restaurantService.getAllRestaurant(request);

        List<Food> foods = foodService.getRestaurantsFood(restaurantId,vegetarian,nonveg,seassonal,food_category);
        return new ResponseEntity<>(foods, HttpStatus.OK);
    }


}