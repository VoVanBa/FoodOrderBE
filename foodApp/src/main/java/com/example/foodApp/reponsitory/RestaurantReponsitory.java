package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantReponsitory extends JpaRepository<Restaurant,Long> {
    Restaurant findByOwnerId(Long id);

    Page<Restaurant>findAll(Pageable pageable);
    @Query("select r from Restaurant r where lower(r.name) like lower(concat('%',:query, '%')) OR lower(r.cuisineType)" +
            "like lower(concat('%',:query, '%'))")
    Page<Restaurant> findBySearchQuery(Pageable pageable,String query);

}
