package com.example.youtube.dto.comment;

import com.example.youtube.dto.video.VideoDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDTO {
    private Integer id;
    private Integer profileId;
    private String videoId;
    private String content;
    private Integer replyId;
    private LocalDateTime createdDate = LocalDateTime.now();

}
