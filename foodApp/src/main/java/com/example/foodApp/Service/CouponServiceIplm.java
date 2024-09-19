package com.example.foodApp.Service;

import com.example.foodApp.model.Coupon;
import com.example.foodApp.model.CouponCondition;
import com.example.foodApp.model.User;
import com.example.foodApp.reponsitory.CartItemRepository;
import com.example.foodApp.reponsitory.CouponConditionRepository;
import com.example.foodApp.reponsitory.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class CouponServiceIplm implements CouponService{
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    CouponConditionRepository couponConditionRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Override
    public Double caculationCoupon(String couponCode, double   totalAmount) {
        Coupon coupon=couponRepository.findByCode(couponCode).orElseThrow(()->new IllegalArgumentException("Coupon not found"));
        if (!coupon.isActive()) {
            throw new IllegalArgumentException("Coupon is not active");
        }

        double discount = calculateDiscount(coupon, totalAmount);
       double finalAmount = totalAmount - discount;
        return discount;
    }

    private double calculateDiscount(Coupon coupon, double totalAmount) {
        List<CouponCondition> conditions = couponConditionRepository
                .findByCouponId(coupon.getId());
        double discount = 0.0;
        double updatedTotalAmount = totalAmount;
        for (CouponCondition condition : conditions) {
            //EAV(Entity - Attribute - Value) Model
            String attribute = condition.getAttribute();
            String operator = condition.getOperator();
            String value = condition.getValue();

            double percentDiscount = Double.valueOf(
                    String.valueOf(condition.getDiscountAmount()));

            if (attribute.equals("minimum")) {
                if (operator.equals(">") && updatedTotalAmount > Double.parseDouble(value)) {
                    discount += updatedTotalAmount * percentDiscount / 100;
                }
            } else if (attribute.equals("applicable_date")) {
                LocalDate applicableDate = LocalDate.parse(value);
                LocalDate currentDate = LocalDate.now();
                if (operator.equalsIgnoreCase("BETWEEN")
                        && currentDate.isEqual(applicableDate)) {
                    discount += updatedTotalAmount * percentDiscount / 100;
                }
            }
            //còn nhiều nhiều điều kiện khác nữa
            updatedTotalAmount = updatedTotalAmount - discount;
        }
        return discount;
    }
}
