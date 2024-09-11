package com.shakhawat.journalapp.repository;

import com.shakhawat.journalapp.entity.ConfigJournal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalRepository extends MongoRepository<ConfigJournal, ObjectId> {

}
