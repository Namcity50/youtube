package com.example.youtube.dto.comment;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO {
    private Integer id;
    private Integer profile_id;
    private String video_id;
    private String content;
    private Integer reply_id;
    private Integer like_count;
    private Integer dislike_count;
    private LocalDateTime create_date;
}
