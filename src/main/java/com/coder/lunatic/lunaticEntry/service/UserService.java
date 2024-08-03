package com.coder.lunatic.lunaticEntry.service;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.entity.User;
import com.coder.lunatic.lunaticEntry.repository.LunaticRepository;
import com.coder.lunatic.lunaticEntry.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {
    @Autowired
    UserRepository repo;

    private static final PasswordEncoder encoder = new BCryptPasswordEncoder();

    public boolean saveNewUser(User user){
        log.info("Enter saveNewUser method");
        try {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            repo.save(user);
            log.info("Exit saveNewUser method");
            return true;
        }catch (Exception e){
            log.error("Exception occur for {}",user.getUserName(),e.getMessage());
            return false;
        }
    }

    public void saveUser(User user){
        repo.save(user);
    }

    public List<User> getUser(){
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

    public void addNewAdmin(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
        repo.save(user);
    }
}
