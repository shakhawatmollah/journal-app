package com.shakhawat.journalapp.repository;

import com.shakhawat.journalapp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByUserName(String username);

    void deleteByUserName(String username);

}
