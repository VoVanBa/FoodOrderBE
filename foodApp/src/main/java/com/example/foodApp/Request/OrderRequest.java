package com.example.foodApp.Request;

import com.example.foodApp.model.Address;
import com.example.foodApp.model.CouponCondition;
import lombok.Getter;

@Getter
public class OrderRequest {
    private Long restaurantId;
    private Address deliveryAddress;
    private Long total;
}
