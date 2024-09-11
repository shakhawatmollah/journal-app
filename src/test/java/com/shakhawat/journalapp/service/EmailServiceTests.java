package com.shakhawat.journalapp.service;

import com.shakhawat.journalapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Disabled
    @Test
    void testSendMail() {
        emailService.sendEmail("shakhawat.hossain@yopmail.com",
                "Testing Java mail sender",
                "Hi, Is everything okay?");
    }
}
