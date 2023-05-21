package com.example.youtube.dto.playList;

import com.example.youtube.dto.channel.ChannelPlayListShortInfoDTO;
import com.example.youtube.dto.channel.ChannelShortInfoDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.dto.video.VideoPlayListShortInfoDTO;
import com.example.youtube.entity.ChannelEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListShortInfoDTO {
    private Integer id;
    private String name;
    private LocalDateTime created_date;
    private ChannelPlayListShortInfoDTO channel; //(id, name),
    private Integer videoCount;
    private List<VideoPlayListShortInfoDTO> videoList; //[{id,name,duration}] (first 2)
}
