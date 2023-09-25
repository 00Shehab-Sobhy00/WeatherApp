package com.example.backend.model.entity;



import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "users")
@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "name")
    private String  name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "phone")
    private Integer phone;


    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    private ERole roles ;

    @OneToMany(mappedBy = "user")
    private List<WeatherNotes> notes;

//          for super admin
//INSERT INTO users (name, email, password, phone, roles)
//    VALUES ('super admin', 'superAdmin@gmail.com', '$2a$10$Mg1lN94ho.MrjEW45Hi6qefXY5RBFKNHRdt2frFWJY2dV46sMJ/Ii',0123, 'SUPER_ADMIN');

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.name()));
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

// when spring security starts and set up the application it will use an object called UserDetails to make spring security life easy
//and this UserDetails is an interface that contains punch of methods























