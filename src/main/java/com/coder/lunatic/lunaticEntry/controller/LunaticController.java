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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lunatic")
public class LunaticController {

    @Autowired
    LunaticService service;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllEntries(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(authentication.getName());
        List<LunaticEntry> response= user.getEntries();
        if(response != null && !response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity <>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody LunaticEntry myEntry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            service.saveEntry(myEntry, authentication.getName());
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<LunaticEntry> entries = user.getEntries().stream().filter(x -> x.getId().equals(myId)).collect(Collectors.toList());
        if(!entries.isEmpty()) {
            Optional<LunaticEntry> entry = service.getEntryById(myId);
            if (entry.isPresent()) {
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean removed = service.deleteById(myId, authentication.getName());
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @RequestBody LunaticEntry myEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<LunaticEntry> entries = user.getEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        if(!entries.isEmpty()) {
            Optional<LunaticEntry> lunaticEntries = service.getEntryById(id);
            if(lunaticEntries.isPresent()) {
                LunaticEntry lunaticEntry = lunaticEntries.get();
                lunaticEntry.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : lunaticEntry.getTitle());
                lunaticEntry.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : lunaticEntry.getContent());
                service.saveEntry(lunaticEntry, authentication.getName());
                return new ResponseEntity<>(lunaticEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
