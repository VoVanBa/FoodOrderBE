package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Cart;
import com.example.foodApp.model.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
//    @Modifying
//    @Transactional
//    @Query("UPDATE CartItem ci SET ci.totalPrice = :newPrice WHERE ci.cart.customer.id = :customerId")
//    void updateTotalPrice(@Param("newPrice") double newPrice, @Param("customerId") Long customerId);



}
