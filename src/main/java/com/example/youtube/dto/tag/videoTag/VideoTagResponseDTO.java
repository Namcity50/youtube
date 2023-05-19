package com.example.youtube.dto.tag.videoTag;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoTagResponseDTO {
    private String videoId;
    private Integer tagId;
    private LocalDateTime createdDate;
}
