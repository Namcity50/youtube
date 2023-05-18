package com.example.youtube.dto.playList;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.example.youtube.dto.channel.ChannelPlayListInfoDTO;
import com.example.youtube.dto.profile.ProfilePlayListInfoDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.enums.PlayListEnums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListInfoDTO {
     private Integer id;
     private String name;
     private String description;
     private PlayListEnums status;
     private Integer order_num;
     private ChannelPlayListInfoDTO channel;
     private ProfilePlayListInfoDTO profile;
}
