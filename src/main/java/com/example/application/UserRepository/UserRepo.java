package com.example.application.UserRepository;

import com.example.application.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository(value = "users")
public interface UserRepo extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

}
