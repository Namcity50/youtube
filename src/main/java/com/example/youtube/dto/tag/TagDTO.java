package com.example.youtube.dto.tag;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TagDTO {
    private Integer id;
    @NotBlank(message = "Name required")
    private String name;
    private LocalDateTime createdDate;

}
