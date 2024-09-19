package com.example.foodApp.Service;

import com.example.foodApp.Request.AddressRequest;
import com.example.foodApp.model.Address;
import com.example.foodApp.model.User;
import com.example.foodApp.reponsitory.AddressResponsitory;
import com.example.foodApp.reponsitory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService{
    @Autowired
    private AddressResponsitory addressResponsitory;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Address findAddressById(Long addressId) {
        return addressResponsitory.findAddressById(addressId);
    }
    @Override
    public void updateAddress(AddressRequest address, Long addressId) throws Exception {
        Address addressExits=findAddressById(addressId);
        if(addressExits==null){
            throw new Exception("address not found");
        }
        addressExits.setFullName(address.getFullName());
        addressExits.setState(address.getState());
        addressExits.setCity(address.getCity());
        addressExits.setCountry(address.getCountry());
        addressExits.setStreetAddress(address.getStreetAddress());

        addressResponsitory.save(addressExits);
    }

    @Override
    public List<Address> findAllByUserId(Long userId) {
        List<Address> addressList= addressResponsitory.findAllAddressByUserId(userId);
        return addressList;
    }
}
