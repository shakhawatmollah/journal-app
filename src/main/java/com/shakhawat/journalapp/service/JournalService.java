package com.shakhawat.journalapp.service;

import com.shakhawat.journalapp.entity.Journal;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class JournalService {

    private final JournalRepository journalRepository;

    private final UserService userService;
    
    //@Transactional
    public void saveEntry(Journal journal, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journal.setDate(LocalDateTime.now());
            Journal saved = journalRepository.save(journal);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            log.error("An error occurred while saving the entry: ", e);
        }
    }

    public void saveEntry(Journal journal) {
        journalRepository.save(journal);
    }

    public List<Journal> getAll() {
        return journalRepository.findAll();
    }

    public Optional<Journal> findById(ObjectId id) {
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            User user = userService.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the entry: ", e);
        }
        return removed;
    }

}