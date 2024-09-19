package com.example.foodApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    public ResponseEntity<?> HomeControler(){
        return new ResponseEntity<>("home", HttpStatus.OK);
    }
}
