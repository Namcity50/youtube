package com.example.youtube.mapper;

import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.entity.VideoEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class VideoPlayListInfo {
    private String id;
    private String title;
    private String previewAttachLink;
    private LocalDateTime publishedDate;
    private Integer viewCount;

}
