package com.example.youtube.dto.playListVideo;

import com.example.youtube.dto.channel.ChannelPlayListShortInfoDTO;
import com.example.youtube.dto.video.VideoPlaylistVideoInfoDTO;
import com.example.youtube.dto.video.VideoResponseDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistVideoInfoDTO {
     private  Integer playlistId;
     private VideoPlaylistVideoInfoDTO video; //(id, preview_attach(id, url),title,duration),
     private ChannelPlayListShortInfoDTO channel; //(id, name)
     private LocalDateTime createdDate;
     private Integer orderNum;
}
