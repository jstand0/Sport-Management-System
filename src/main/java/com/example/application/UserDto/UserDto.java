package com.example.application.UserDto;


import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}