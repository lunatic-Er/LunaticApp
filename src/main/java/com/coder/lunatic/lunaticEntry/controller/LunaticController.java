package com.coder.lunatic.lunaticEntry.controller;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.service.LunaticService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lunatic")
public class LunaticController {

    @Autowired
    LunaticService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<LunaticEntry> response= service.getEntry();
        if(response != null && !response.isEmpty()) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity <>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody LunaticEntry myEntry){
        try {
            service.saveEntry(myEntry);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{myId}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId myId){
        Optional<LunaticEntry> entry = service.getEntryById(myId);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteEntryById(@PathVariable ObjectId myId){
        service.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable ObjectId id, @RequestBody LunaticEntry myEntry){
        LunaticEntry lunaticEntry = service.getEntryById(id).orElse(null);
        if(lunaticEntry != null){
            lunaticEntry.setTitle(myEntry.getTitle() != null && !myEntry.getTitle().equals("") ? myEntry.getTitle() : lunaticEntry.getTitle());
            lunaticEntry.setContent(myEntry.getContent() != null && !myEntry.getContent().equals("") ? myEntry.getContent() : lunaticEntry.getContent());
            service.saveEntry(lunaticEntry);
            return new ResponseEntity<>(lunaticEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
