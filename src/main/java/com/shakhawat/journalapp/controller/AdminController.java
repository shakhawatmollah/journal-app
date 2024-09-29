package com.shakhawat.journalapp.controller;

import com.shakhawat.journalapp.cache.AppCache;
import com.shakhawat.journalapp.dto.UserDTO;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Tag(name = "Admin APIs", description = "Get users, Create admin user, Clear cache")
public class AdminController {

    private final UserService userService;

    private final AppCache appCache;

    @GetMapping("/all-users")
    @Operation(summary = "Get all user")
    public ResponseEntity<Object> getAllUsers() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    @Operation(summary = "Create admin user")
    public void createUser(@RequestBody UserDTO userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setSentimentAnalysis(userDto.getIsSentimentAnalysis());
        user.setPassword(userDto.getPassword());
        userService.saveAdmin(user);
    }

    @GetMapping("clear-app-cache")
    @Operation(summary = "Reset cache")
    public void clearAppCache(){
        appCache.init();
    }

}
