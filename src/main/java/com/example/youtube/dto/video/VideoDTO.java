package com.example.youtube.dto.video;

import com.example.youtube.enums.VideoType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class VideoDTO {
    private String id;
    private String preview_attach_id;
    private String title;
    private Integer categoryId;
    private String attachId;
    private VideoType type;
    private Integer view_count;
    private Integer shared_count;
    private String description;
    private String channelId;
    private Integer like_count;
    private Integer dislike_count;
}
