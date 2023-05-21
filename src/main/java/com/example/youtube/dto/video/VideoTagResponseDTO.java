package com.example.youtube.dto.video;

import com.example.youtube.dto.tag.TagResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class VideoTagResponseDTO {
    private Integer id;
    private String videoId;
    private TagResponseDTO tag;
    private LocalDateTime createdDate;
}
