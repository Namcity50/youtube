package com.example.youtube.mapper;

import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.video.VideoShortInfoDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoOwnerPlayListInfoDTO {
    private VideoShortInfoDTO video;
    private PlaylistDTO playList;
    private ProfileDTO profile;

    public VideoOwnerPlayListInfoDTO(VideoShortInfoDTO video, PlaylistDTO playList, ProfileDTO profile) {
        this.video = video;
        this.playList = playList;
        this.profile = profile;
    }

    public VideoOwnerPlayListInfoDTO() {
    }
}
