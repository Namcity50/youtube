package com.example.youtube.dto.video;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.channel.ChannelDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideoShortInfoDTO {
    private String id;
    private String title;
    private AttachDTO previewAttach;      //(id, url),
    private LocalDateTime publishedDate;
    private ChannelDTO channelDTO; //channel(id,name,photo(url)),
   private Integer  viewCount;
   private String duration;
}
