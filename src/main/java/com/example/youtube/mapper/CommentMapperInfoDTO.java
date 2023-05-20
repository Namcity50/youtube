package com.example.youtube.mapper;

import com.example.youtube.dto.attach.AttachDTO;

import java.time.LocalDateTime;

public interface CommentMapperInfoDTO {
    Integer getId();
    Integer getProfileId();
    LocalDateTime getCreatedDate();
    String getContent();
    Integer getLikeCount();
    Integer getDisLikeCount();
    String getProfileName();
    String getProfileSurname();
    AttachDTO getPhoto();
}
