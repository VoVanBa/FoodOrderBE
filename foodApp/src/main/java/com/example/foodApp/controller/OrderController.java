package com.example.foodApp.controller;

import com.example.foodApp.Request.AddCartItemRequest;
import com.example.foodApp.Request.OrderRequest;
import com.example.foodApp.Service.OrderService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.CartItem;
import com.example.foodApp.model.Order;
import com.example.foodApp.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    private  final Logger logger =LoggerFactory.getLogger(OrderController.class);

    @PostMapping("")
    public ResponseEntity<?> createOrder(
            @RequestBody OrderRequest request,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        Order order= orderService.createOrder(request,user);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getOrderHistory(

            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> ordesr= orderService.getUsersOrder(user.getId());
        return new ResponseEntity<>(ordesr, HttpStatus.OK);
    }
}
