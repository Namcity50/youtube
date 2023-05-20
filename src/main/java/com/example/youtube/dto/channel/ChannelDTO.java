package com.example.youtube.dto.channel;

import com.example.youtube.entity.AttachEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.GeneralStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)

public class ChannelDTO {
    private String id;
    private String name;
    private String description;
    private GeneralStatus status;
    private String photoId;
    private String bannerId;
    private Integer profileId;

    public ChannelDTO(String id, String name, String photoId) {
        this.id = id;
        this.name = name;
        this.photoId = photoId;
    }

    public ChannelDTO() {
    }
}
