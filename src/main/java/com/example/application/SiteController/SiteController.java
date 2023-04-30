package com.example.application.SiteController;

import com.example.application.Role.Role;
import com.example.application.RoleRepository.RoleRepository;
import com.example.application.User.User;
import com.example.application.UserDto.UserDto;
import com.example.application.UserRepository.UserRepository;
import com.example.application.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class SiteController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Lazy
    @Autowired
    private PasswordEncoder bcryptEncoderStart;

    @Autowired
    AuthService authService;


    @GetMapping("")
    public String hello() {
        return "aa";
    }
    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody JWTRequest jwtRequest){
        String token = "";
        try {
            token = authService.createAuthenticationToken(jwtRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto){

        // add check for username exists in a DB
        if(userRepository.existsByUsername(userDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if(userRepository.existsByEmail(userDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(bcryptEncoderStart.encode(userDto.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);

    }
}