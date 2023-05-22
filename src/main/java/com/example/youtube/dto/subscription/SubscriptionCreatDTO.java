package com.example.youtube.dto.subscription;

import com.example.youtube.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubscriptionCreatDTO {
    @NotBlank(message = "channelId must have been some value")
    private String channelId;
    private NotificationType notificationType;
}
