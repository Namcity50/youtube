package com.example.youtube.dto;

import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class SubscriptionDTO {
    private Integer id;
    private Integer profileId;
    private String channelId;
    private LocalDateTime createdDate;
    private LocalDateTime unSubscribeDate;
    private GeneralStatus status;
    private NotificationType notification;

}
