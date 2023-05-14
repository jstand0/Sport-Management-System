package com.example.application.SiteController;

import com.example.application.RoleRepository.RoleRepository;
import com.example.application.User.User;
import com.example.application.UserDto.UserDto;
import com.example.application.UserRepository.UserRepository;
import com.example.application.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody JWTRequest jwtRequest) {
        String token = "Security";
        try {
            token = authService.createAuthenticationToken(jwtRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        // check that all required fields are present
        if (userDto.getUsername() == null || userDto.getEmail() == null || userDto.getPassword() == null) {
            String errorMessage = "Please provide the following required fields: ";
            if (userDto.getUsername() == null) {
                errorMessage += "username, ";
            }
            if (userDto.getEmail() == null) {
                errorMessage += "email, ";
            }
            if (userDto.getPassword() == null) {
                errorMessage += "password, ";
            }
            errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        // add check for username exists in a DB
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        // add check for email exists in DB
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
        }

        // create user object
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(bcryptEncoderStart.encode(userDto.getPassword()));

        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        // try to find the user in the database
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // update the user's fields if they are present in the request body
            if (userDto.getUsername() != null) {
                // check if the username is already taken by another user
                if (userRepository.existsByUsernameAndIdIsNot(userDto.getUsername(), id)) {
                    return new ResponseEntity<>("Username is already taken", HttpStatus.BAD_REQUEST);
                }
                user.setUsername(userDto.getUsername());
            }
            if (userDto.getEmail() != null) {
                // check if the email is already taken by another user
                if (userRepository.existsByEmailAndIdIsNot(userDto.getEmail(), id)) {
                    return new ResponseEntity<>("Email is already taken", HttpStatus.BAD_REQUEST);
                }
                user.setEmail(userDto.getEmail());
            }
            if (userDto.getFirstName() != null) {
                user.setFirstName(userDto.getFirstName());
            }
            if (userDto.getLastName() != null) {
                user.setLastName(userDto.getLastName());
            }
            if (userDto.getPassword() != null) {
                user.setPassword(bcryptEncoderStart.encode(userDto.getPassword()));
            }

            userRepository.save(user);

            // convert the updated user to a user DTO and return it
            UserDto updatedUserDto = new UserDto();
            updatedUserDto.setId(user.getId());
            updatedUserDto.setUsername(user.getUsername());
            updatedUserDto.setEmail(user.getEmail());
            updatedUserDto.setFirstName(user.getFirstName());
            updatedUserDto.setLastName(user.getLastName());
            updatedUserDto.setPassword(user.getPassword());

            return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
        } else {
            // if the user does not exist, return a 404 Not Found response
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/all")
    public ResponseEntity<?> deleteAllUsers() {
        userRepository.deleteAll();
        return new ResponseEntity<>("All users deleted successfully", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        // check if user exists in the database
        Optional<User> optionalUser = userRepository.findById(id);
        if (!optionalUser.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        // delete the user from the database
        userRepository.deleteById(id);

        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }


    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        // get all users from the database
        List<User> users = userRepository.findAll();

        // create a list of user DTOs to return
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setPassword(user.getPassword());
            userDtos.add(userDto);
        }
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        // try to find the user in the database
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            // if the user exists, convert it to a user DTO and return it
            User user = optionalUser.get();
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setUsername(user.getUsername());
            userDto.setEmail(user.getEmail());
            userDto.setFirstName(user.getFirstName());
            userDto.setLastName(user.getLastName());
            userDto.setPassword(user.getPassword());
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            // if the user does not exist, return a 404 Not Found response
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}