package com.coder.lunatic.lunaticEntry.repository;

import com.coder.lunatic.lunaticEntry.entity.LunaticEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LunaticRepository extends MongoRepository<LunaticEntry, ObjectId> {
}
