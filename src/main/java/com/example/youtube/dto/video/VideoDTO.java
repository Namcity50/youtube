package com.example.youtube.dto.video;

import com.example.youtube.enums.VideoType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoDTO {
    private String id;
    private String previewAttachId;
    @NotBlank(message = "In title must be some value")
    private String title;
    private Integer categoryId;
    @NotBlank(message = "attachId required")
    private String attachId;
    private VideoType type;
    private Integer viewCount;
    private Integer sharedCount;
    private String description;
    private String channelId;
    private Integer likeCount;
    private Integer dislikeCount;
}
