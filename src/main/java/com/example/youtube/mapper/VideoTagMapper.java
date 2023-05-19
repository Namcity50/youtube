package com.example.youtube.mapper;

import java.time.LocalDateTime;

public interface VideoTagMapper {
    Integer getId();
    String getVideoId();
    Integer getTagId();
    String getTagName();
    LocalDateTime getCreatedDate();
}
