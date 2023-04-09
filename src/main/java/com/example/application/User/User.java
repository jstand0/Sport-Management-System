package com.example.application.User;


import com.example.application.Role.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Table;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Table(appliesTo = "users")
@Setter
@NoArgsConstructor
@Entity
public class User {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "USERS_ID", referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "ROLES_ID", referencedColumnName = "ID")})
    private List<Role> roles = new ArrayList<>();

}


