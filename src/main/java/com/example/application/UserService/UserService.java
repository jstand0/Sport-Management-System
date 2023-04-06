package com.example.application.UserService;


import com.example.application.User.User;
import com.example.application.UserDto.UserDto;

import javax.persistence.Entity;
import java.util.List;
@Entity
public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}