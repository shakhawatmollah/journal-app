package com.shakhawat.journalapp.controller;

import com.shakhawat.journalapp.dto.LoginDTO;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.repository.UserRepository;
import com.shakhawat.journalapp.response.WeatherResponse;
import com.shakhawat.journalapp.service.UserService;
import com.shakhawat.journalapp.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User APIs", description = "Update user, Delete user, Check Weather")
public class UserController {

    private final UserService userService;

    private final UserRepository userRepository;

    private final WeatherService weatherService;

    @PutMapping
    @Operation(summary = "Update user")
    public ResponseEntity<?> updateUser(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(loginDTO.getUserName());
        userInDb.setPassword(loginDTO.getPassword());
        userService.saveNewUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Operation(summary = "Delete user")
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{city}")
    @Operation(summary = "Greeting with weather feels like")
    public ResponseEntity<?> greeting(@PathVariable String city) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather(city);
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

}