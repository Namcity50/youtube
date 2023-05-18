package com.example.youtube.dto.video;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.channel.ChannelDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideShortInfoDTO {
    private String id;
    private String title;
    private AttachDTO preview_attach;      //(id, url),
    private LocalDateTime published_date;
    private ChannelDTO channelDTO; //channel(id,name,photo(url)),
   private Integer  view_count;
   private String duration;
}
