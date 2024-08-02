package com.coder.lunatic.lunaticEntry.controller;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.repository.UserRepository;
import com.coder.lunatic.lunaticEntry.service.LunaticService;
import com.coder.lunatic.lunaticEntry.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> response= service.getEntry();
        if(response != null && !response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity <>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody User myEntry){
        try {
            service.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateEntryById(@RequestBody User user, @PathVariable String username){
        User userInDb = service.findByUserName(username);
        if(userInDb != null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            service.saveEntry(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
