package com.example.foodApp.Request;

import lombok.Getter;

@Getter
public class UpdateCartItemRequest {
    private Long cartItemId;
    private int quantity;
}
