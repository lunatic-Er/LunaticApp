package com.coder.lunatic.lunaticEntry.service;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.repository.LunaticRepository;
import com.coder.lunatic.lunaticEntry.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class UserService {
    @Autowired
    UserRepository repo;

    public void saveEntry(User user){
        repo.save(user);
    }

    public List<User> getEntry(){
        return repo.findAll();
    }

    public Optional<User> getEntryById(ObjectId id){
        return repo.findById(id);
    }

    public void deleteById(ObjectId id){
        repo.deleteById(id);
    }

    public void updateEntry(User user){
        repo.save(user);
    }

    public User findByUserName(String userName){
        return repo.findByUserName(userName);
    }
}
