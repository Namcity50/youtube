package com.example.youtube.mapper;

import java.time.LocalDateTime;

public interface PlaylistVideoInfoMapper {
    Integer getPlayListId();
    String getVideoId();
    String getPreviewId();
    String getVideoTitle();
    LocalDateTime getVideoDuration();
    String getChannelId();
    String getChannelName();
    LocalDateTime getCreatedDate();
    Integer getOrderNum();
}
