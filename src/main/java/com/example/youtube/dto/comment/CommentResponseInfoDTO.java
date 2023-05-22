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
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer dislikeCount;
    private ProfileDTO profileDTO;
}
