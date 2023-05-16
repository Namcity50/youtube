package com.example.youtube.dto.category;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CategoryDTO {
    private Integer id;
    private String name;
    private LocalDateTime created_date;
    private Boolean visible;
}
