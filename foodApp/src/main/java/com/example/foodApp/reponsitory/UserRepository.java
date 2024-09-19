package com.example.foodApp.reponsitory;

import com.example.foodApp.model.Address;
import com.example.foodApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
     User findByEmail(String username);

}
