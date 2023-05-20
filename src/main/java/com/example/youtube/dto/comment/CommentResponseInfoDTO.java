package com.example.youtube.dto.comment;

import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.video.VideoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseInfoDTO {
    private Integer id;
    private String content;
    private LocalDateTime created_date;
    private Integer like_count;
    private Integer dislike_count;
    private ProfileDTO profileDTO;
}
