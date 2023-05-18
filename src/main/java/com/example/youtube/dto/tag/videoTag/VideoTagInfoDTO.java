package com.example.youtube.dto.tag.videoTag;

import com.example.youtube.dto.tag.TagShortDTO;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoTagInfoDTO {
    private Integer id;
    private String videoId;
    private TagShortDTO tagShortDTO;
    private LocalDateTime createdDate;
}
