package com.shakhawat.journalapp.service;

import com.shakhawat.journalapp.entity.Journal;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.repository.JournalRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

public class JournalServiceEntryTest {

    @Mock
    private UserService userService;

    @Mock
    private JournalRepository journalRepository;

    public JournalServiceEntryTest() {
        MockitoAnnotations.openMocks(this);
    }

    private JournalService MakeJournalServiceWithMocks(UserService mockUserService, JournalRepository mockJournalRepository) {
        return new JournalService(mockJournalRepository, mockUserService);
    }

    @Test
    public void SaveEntry_NonExistentUser_DoesNotThrowException() {
        JournalService journalService = MakeJournalServiceWithMocks(userService, journalRepository);
        Journal journal = new Journal();
        journal.setTitle("Test Title");
        journal.setContent("Test Content");

        final String userName = "nonExistentUser";
        when(userService.findByUserName(userName)).thenReturn(null);

        // Act: invoking the entry point
        assertDoesNotThrow(() -> journalService.saveEntry(journal, userName));
    }

    @Test
    public void SaveEntry_NonExistentUser_DoesNotCallRepositorySave() {
        JournalService journalService = MakeJournalServiceWithMocks(userService, journalRepository);
        Journal journal = new Journal();
        journal.setTitle("Test Title");
        journal.setContent("Test Content");

        final String userName = "nonExistentUser";
        when(userService.findByUserName(userName)).thenReturn(null);

        // Act: invoking the entry point
        journalService.saveEntry(journal, userName);

        // Assert: third-party interaction check
        verify(journalRepository, never()).save(any(Journal.class));
    }

    @Test
    public void SaveEntry_NonExistentUser_DoesNotCallUserServiceSave() {
        JournalService journalService = MakeJournalServiceWithMocks(userService, journalRepository);
        Journal journal = new Journal();
        journal.setTitle("Test Title");
        journal.setContent("Test Content");

        final String userName = "nonExistentUser";
        when(userService.findByUserName(userName)).thenReturn(null);

        // Act: invoking the entry point
        journalService.saveEntry(journal, userName);

        // Assert: third-party interaction check
        verify(userService, never()).saveUser(any(User.class));
    }

}