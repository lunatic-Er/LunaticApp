package com.coder.lunatic.lunaticEntry.service;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.repository.LunaticRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class LunaticService {
    @Autowired
    private LunaticRepository repo;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(LunaticEntry lunaticEntry, String username){
        try {
            User user = userService.findByUserName(username);
            lunaticEntry.setDate(LocalDateTime.now());
            LunaticEntry saved = repo.save(lunaticEntry);
            user.getEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An Exception occur "+ e);
        }
    }

    public void saveEntry(LunaticEntry lunaticEntry) {
        repo.save(lunaticEntry);
    }

    public List<LunaticEntry> getEntry(){
        return repo.findAll();
    }

    public Optional<LunaticEntry> getEntryById(ObjectId id){
        return repo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username){
        boolean removed = false;
        try {
            User user = userService.findByUserName(username);
            removed = user.getEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                repo.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An exception occur "+ e);
        }
        return removed;
    }

    public void updateEntry(LunaticEntry LunaticEntry){
        repo.save(LunaticEntry);
    }
}
