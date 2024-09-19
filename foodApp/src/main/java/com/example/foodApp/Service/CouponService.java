package com.example.foodApp.Service;

import com.example.foodApp.model.User;

public interface CouponService {
    Double caculationCoupon(String couponCode, double totalAmount);
}
