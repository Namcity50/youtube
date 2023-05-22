package com.example.youtube.dto;

import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.enums.NotificationType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubscriptionInfoDTO {
    private Integer id;
    private ChannelDTO channelDTO;
    private NotificationType notificationType;
    private LocalDateTime createdDate;
}
