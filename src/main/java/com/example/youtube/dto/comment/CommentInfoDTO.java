package com.example.youtube.dto.comment;


import com.example.youtube.dto.profile.ProfileDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentInfoDTO {
    private Integer id;
    private String content;
    private LocalDateTime created_date;
    private Integer like_count;
    private Integer dislike_count;
    private ProfileDTO profileDTO;



}
