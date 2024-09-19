package com.example.foodApp.controller;

import com.example.foodApp.Service.CategoryService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.Category;
import com.example.foodApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @PostMapping("/admin/category")
    public ResponseEntity<?> createCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization")  String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Category createCategory=categoryService.createCategory(category.getName(),user.getId());
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @PutMapping("/admin/category")
    public ResponseEntity<?> updateCategory(
            @RequestBody Category category,
            @RequestHeader("Authorization")  String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);

        Category createCategory=categoryService.updateCategory(category.getName(),user.getId(),category.getId());
        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }


    @GetMapping("/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategory(
          //  @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id) throws Exception {
       // User user=userService.findUserByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/admin/category/restaurant/{id}")
    public ResponseEntity<List<Category>> getRestaurantCategoryAdmin(
              @RequestHeader("Authorization")  String jwt,
            @PathVariable Long id) throws Exception {
         User user=userService.findUserByJwtToken(jwt);
        List<Category> categories=categoryService.findCategoryByRestaurantId(id);
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
