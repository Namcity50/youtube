package com.example.youtube.dto;

import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.NotificationType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionUpdateDTO {
    @NotBlank(message = "channelId must have been some value")
    private String channelId;
    private GeneralStatus status;
    private NotificationType notificationType;
}
