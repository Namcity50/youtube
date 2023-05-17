package com.example.youtube.dto;

import com.example.youtube.enums.PlayListEnums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlaylistDTO {
    private Integer id;
    private String name;
    private String channelId;
    private String description;
    private PlayListEnums status;
    private Integer orderNumber;
}
