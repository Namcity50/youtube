package com.example.youtube.mapper;

import java.time.LocalDateTime;

public interface CommentMapperDTO {

    Integer getId();

    Integer getVideoId();

    Object getPreviewAttachId();

    Object getTitle();

    LocalDateTime getCreatedDate();

    String getContent();

    Integer getLikeCount();

    Integer getDisLikeCount();
}
