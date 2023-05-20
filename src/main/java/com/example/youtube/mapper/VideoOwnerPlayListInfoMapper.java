package com.example.youtube.mapper;

import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.entity.VideoEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoOwnerPlayListInfoMapper {
    private VideoEntity video;
    private PlayListEntity playList;
    private ProfileEntity profile;

    public VideoOwnerPlayListInfoMapper(VideoEntity video, PlayListEntity playList, ProfileEntity profile) {
        this.video = video;
        this.playList = playList;
        this.profile = profile;
    }
}
