package com.example.foodApp.Request;

import lombok.Getter;

import java.util.List;

@Getter
public class AddCartItemRequest {
    private Long foodId;
    private int quantity;
    private List<String> ingredient;

}
