package com.example.youtube.mapper;

import java.time.LocalDateTime;

public interface PlayListShortInfoMapper {
    Integer getId();
    String getName();
    LocalDateTime getCreatedDate();
    String getChannelId();
    String getChannelName();
    Integer getVideoCount();
    String getVideoId();
    String getVideoName();
    String getVideoDuration();
    Integer getViewCount();
    LocalDateTime getUpdatedDate();
}
