package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRespository extends JpaRepository<Orderitem,Long> {

}
