package com.coder.lunatic.lunaticEntry.service;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import com.coder.lunatic.lunaticEntry.repository.LunaticRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class LunaticService {
    @Autowired
    LunaticRepository repo;

    public void saveEntry(LunaticEntry lunaticEntry){
        lunaticEntry.setDate(LocalDateTime.now());
        repo.save(lunaticEntry);
    }

    public List<LunaticEntry> getEntry(){
        return repo.findAll();
    }

    public Optional<LunaticEntry> getEntryById(ObjectId id){
        return repo.findById(id);
    }

    public void deleteById(ObjectId id){
        repo.deleteById(id);
    }

    public void updateEntry(LunaticEntry LunaticEntry){
        repo.save(LunaticEntry);
    }
}
