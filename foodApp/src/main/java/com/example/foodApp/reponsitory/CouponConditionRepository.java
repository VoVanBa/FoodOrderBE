package com.example.foodApp.reponsitory;

import com.example.foodApp.model.CouponCondition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponConditionRepository extends JpaRepository<CouponCondition,Long> {
    List<CouponCondition>findByCouponId(Long couponId);
}
