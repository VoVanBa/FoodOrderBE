package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryResponsitory extends JpaRepository<Category,Long> {
    List<Category> findByRestaurantId(Long id);
}
