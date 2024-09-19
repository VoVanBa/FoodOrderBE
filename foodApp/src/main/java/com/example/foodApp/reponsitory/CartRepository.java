package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart,Long> {
    Cart findByCustomerId(Long userId);
}
