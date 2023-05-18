package com.example.youtube.dto.channel;

import com.example.youtube.enums.GeneralStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChannelShortInfoDTO {
    private String id;
    private String name;
    private String photoId;
}
