package com.shakhawat.journalapp.controller;

import com.shakhawat.journalapp.dto.LoginDTO;
import com.shakhawat.journalapp.dto.UserDTO;
import com.shakhawat.journalapp.entity.User;
import com.shakhawat.journalapp.service.UserDetailsServiceImpl;
import com.shakhawat.journalapp.service.UserService;
import com.shakhawat.journalapp.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Public APIs", description = "Health check, Signup, Login")
public class PublicController {

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final UserService userService;

    private final JwtUtil jwtUtil;

    @GetMapping("/health-check")
    @Operation(summary = "Application health check")
    public String healthCheck() {
        return "Ok";
    }

    @PostMapping("/signup")
    @Operation(summary = "Signup user")
    public void signup(@RequestBody UserDTO userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setSentimentAnalysis(userDto.getIsSentimentAnalysis());
        user.setPassword(userDto.getPassword());
        userService.saveNewUser(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login to the Journal Application")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginDTO.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }
}
