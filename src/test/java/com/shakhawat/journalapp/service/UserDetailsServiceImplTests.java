package com.shakhawat.journalapp.service;

import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.repository.UserRepository;
import com.shakhawat.journalapp.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ActiveProfiles("dev")
class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(Optional.ofNullable(User.builder().userName("mollah").password("123456").roles(new ArrayList<>()).build()));
        UserDetails user = userDetailsService.loadUserByUsername("mollah");
        Assertions.assertNotNull(user);
    }
}