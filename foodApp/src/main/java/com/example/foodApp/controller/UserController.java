package com.example.foodApp.controller;

import com.example.foodApp.Request.AddressRequest;
import com.example.foodApp.Service.AddressService;
import com.example.foodApp.Service.UserService;
import com.example.foodApp.model.Address;
import com.example.foodApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;

    @GetMapping("/profile")
    public ResponseEntity<?>findUserByJwtToken(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJwtToken(jwt);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/address/{addressId}")
    public ResponseEntity<?> updateAddress(
            @RequestBody AddressRequest addressRequest,
            @PathVariable Long addressId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        addressService.updateAddress(addressRequest, addressId);

        return new ResponseEntity<>("update success", HttpStatus.OK);
    }

    @GetMapping("/address")
    public ResponseEntity<?> getAllAddressUser(
            @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        List<Address> addressList=addressService.findAllByUserId(user.getId());

        return new ResponseEntity<>(addressList, HttpStatus.OK);
    }
}
