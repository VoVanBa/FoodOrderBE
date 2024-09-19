package com.example.foodApp.controller;

import com.example.foodApp.Request.OrderRequest;
import com.example.foodApp.Service.OrderService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.Order;
import com.example.foodApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin/order")
public class AdminOrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> getOrderHistory(
            @PathVariable Long id,
            @RequestParam(required = false )String order_status,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        List<Order> ordesr= orderService.getRestaurantsOrder(id,order_status);
        return new ResponseEntity<>(ordesr, HttpStatus.OK);
    }
    @PutMapping("/{id}/{orderStatus}")
    public ResponseEntity<?> updateOrderStatus(
            @PathVariable Long id,
            @PathVariable String orderStatus,
            @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
      Order ordes= orderService.updateOrder(id,orderStatus);
        return new ResponseEntity<>(ordes, HttpStatus.OK);
    }

}
