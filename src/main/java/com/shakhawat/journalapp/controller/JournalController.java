package com.shakhawat.journalapp.controller;

import com.shakhawat.journalapp.dto.JournalDTO;
import com.shakhawat.journalapp.entity.Journal;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.service.JournalService;
import com.shakhawat.journalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/journal")
@RequiredArgsConstructor
@Tag(name = "Journal APIs")
public class JournalController {

    private final JournalService journalEntryService;

    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all Journal entries of logged-in user")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> journalList = user.getJournalEntries();
        if (!ObjectUtils.isEmpty(journalList)) {
            return new ResponseEntity<>(journalList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    @Operation(summary = "Save Journal Entry")
    public ResponseEntity<Journal> createEntry(@RequestBody JournalDTO journalDTO) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            Journal journal = new Journal();
            journal.setTitle(journalDTO.getTitle());
            journal.setContent(journalDTO.getContent());
            journal.setSentiment(journalDTO.getSentiment());
            journalEntryService.saveEntry(journal, userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    @Operation(summary = "Get Journal entry by ID")
    public ResponseEntity<?> getJournalEntryById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).toList();
        if (!collect.isEmpty()) {
            Optional<Journal> journalEntry = journalEntryService.findById(objectId);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    @Operation(summary = "Delete Journal by ID")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable String myId) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean removed = journalEntryService.deleteById(objectId, username);
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("id/{myId}")
    @Operation(summary = "Update Journal")
    public ResponseEntity<?> updateJournalById(@PathVariable String myId, @RequestBody JournalDTO newEntry) {
        ObjectId objectId = new ObjectId(myId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<Journal> collect = user.getJournalEntries().stream().filter(x -> x.getId().equals(objectId)).toList();
        if (!collect.isEmpty()) {
            Optional<Journal> journalEntry = journalEntryService.findById(objectId);
            if (journalEntry.isPresent()) {
                Journal old = journalEntry.get();
                old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
                old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
                journalEntryService.saveEntry(old);
                return new ResponseEntity<>(old, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}