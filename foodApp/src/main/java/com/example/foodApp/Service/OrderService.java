package com.example.foodApp.Service;

import com.example.foodApp.Request.OrderRequest;
import com.example.foodApp.model.Order;
import com.example.foodApp.model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderRequest order, User user) throws Exception;

    Order updateOrder(Long orderId,String orderStatus) throws Exception;

    void calcelOrder(Long orderId) throws Exception;

    List<Order> getUsersOrder(Long userId);

    List<Order> getRestaurantsOrder(Long restaurantId,String orderStatus);

    Order findOrderById(Long orderId) throws Exception;
}
