package com.example.youtube.dto.comment;

import com.example.youtube.dto.video.VideoResponseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponseDTO {
    private Integer id;
    private String content;
    private LocalDateTime createdDate;
    private Integer likeCount;
    private Integer dislikeCount;
    private VideoResponseDTO videoResponseDTO;    //(id, name, preview_attach_id, title, duration)
}
