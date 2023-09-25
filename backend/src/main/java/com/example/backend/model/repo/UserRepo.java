package com.example.backend.model.repo;

import com.example.backend.model.entity.ERole;
import com.example.backend.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo  extends JpaRepository <User,Integer> {

    Optional<User> findByEmail(String email);

    List<User> findByRoles(ERole roles);

    Optional<User> findByName (String userName);
}
