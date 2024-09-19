package com.example.foodApp.Service;

import com.example.foodApp.model.User;

public interface UserService{
    public User findUserByJwtToken(String jwt) throws Exception;

    User findUserByEmail(String email) throws Exception;
}
