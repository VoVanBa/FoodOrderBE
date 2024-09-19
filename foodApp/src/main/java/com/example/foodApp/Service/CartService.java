package com.example.foodApp.Service;

import com.example.foodApp.Request.AddCartItemRequest;
import com.example.foodApp.model.Cart;
import com.example.foodApp.model.CartItem;

public interface CartService {
    CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    CartItem updateCartItemQuantity(Long cartItemId,int quantity) throws Exception;
    Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
    Long caculaterCartTotals(Cart cart);
    Cart findCartById(Long id) throws Exception;
    Cart findCartByUserId(Long userId) throws Exception;
    Cart cleanCart(Long userId) throws Exception;
}
