package com.example.application.UserService;


import com.example.application.User.User;
import com.example.application.UserDto.UserDto;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findByEmail(String email);

    List<UserDto> findAllUsers();
}