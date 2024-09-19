package com.example.foodApp.Service;

import com.example.foodApp.Request.AddressRequest;
import com.example.foodApp.model.Address;

import java.util.List;

public interface AddressService {
    Address findAddressById(Long addressId);
    void updateAddress(AddressRequest address, Long addressId) throws Exception;
    List<Address>findAllByUserId(Long userId);
}
