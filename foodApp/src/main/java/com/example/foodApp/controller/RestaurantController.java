package com.example.foodApp.controller;

import com.example.foodApp.Request.CreateRestaurantResquest;
import com.example.foodApp.Service.RestaurantService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.dto.RestaurantDto;
import com.example.foodApp.model.Restaurant;
import com.example.foodApp.model.User;
import com.example.foodApp.response.RestaurantListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private UserService userService;
    @GetMapping("/search")
    public ResponseEntity<?> searchRestaurant(
          //  @RequestHeader("Authorization") String jwt,
            @RequestParam String keyword,
          @RequestParam(defaultValue = "1") int page ,
          @RequestParam(defaultValue = "8") int limit
    ) throws Exception {
      //  User user=userService.findUserByJwtToken(jwt);
        PageRequest pageRequest = PageRequest.of(page-1, limit);

       Page<Restaurant> restaurantPage= restaurantService.searchRestaurant(pageRequest,keyword);
        int totalPage=restaurantPage.getTotalPages();
        List<Restaurant> restaurant =restaurantPage.getContent();
        return new ResponseEntity<>(RestaurantListResponse.builder()
                .restaurant(restaurant).totalPage(totalPage).build(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllRestaurant(
          //  @RequestHeader("Authorization") String jwt,
             @RequestParam(defaultValue = "1") int page ,
            @RequestParam(defaultValue = "8") int limit

    ) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1, limit);
       // User user=userService.findUserByJwtToken(jwt);
        Page<Restaurant> restaurantPage= restaurantService.getAllRestaurant(pageRequest);
        int totalPage=restaurantPage.getTotalPages();
        List<Restaurant> restaurant =restaurantPage.getContent();
        return new ResponseEntity<>(RestaurantListResponse.builder()
                .restaurant(restaurant).totalPage(totalPage).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> findRestaurantById(
         //   @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
       // User user=userService.findUserByJwtToken(jwt);
        Restaurant restaurant= restaurantService.findRestaurantById(id);
        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }

    @PutMapping("/{id}/add-favorite")
    public ResponseEntity<RestaurantDto> addToFavourite(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long id
    ) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        RestaurantDto restaurant= restaurantService.addFavorites(id,user);

        return new ResponseEntity<>(restaurant, HttpStatus.OK);
    }
}
