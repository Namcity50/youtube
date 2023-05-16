package com.example.youtube.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "category")
public class CategoryEntity {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
     @Column(name = "name")
     private String name;
     @Column(name = "created_date")
     private LocalDateTime created_date = LocalDateTime.now();
    @Column(name = "prt_id")
    private Integer prtId;
    @Column(name = "visible")
    private Boolean visible;
}
