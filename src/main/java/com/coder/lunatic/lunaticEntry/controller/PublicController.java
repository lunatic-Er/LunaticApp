package com.coder.lunatic.lunaticEntry.controller;

import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    UserService service;

    @GetMapping("/health-check")
    public String checkHelthStatus(){
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createEntry(@RequestBody User myEntry){
        try {
            service.saveNewUser(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
