package com.example.backend.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Table
@Entity
public class WeatherNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String cityName;
    String note;
    String date;
    String adminName;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
