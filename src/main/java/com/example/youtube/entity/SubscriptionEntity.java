package com.example.youtube.entity;

import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "subscription")
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;

    @Column(name = "created_date")
    private LocalDateTime createdDate;
    @Column(name = "unsubscribe_date")
    private LocalDateTime unSubscribeDate;
    @Column(name ="status" )
    @Enumerated(EnumType.STRING)
    private GeneralStatus status;
    @Column(name = "notification")
    @Enumerated(EnumType.STRING)
    private NotificationType notification;

    public SubscriptionEntity(Integer id, ChannelEntity channel, NotificationType notification) {
        this.id = id;
        this.channel = channel;
        this.notification = notification;
    }

    public SubscriptionEntity(Integer id, ChannelEntity channel, LocalDateTime createdDate, NotificationType notification) {
        this.id = id;
        this.channel = channel;
        this.createdDate = createdDate;
        this.notification = notification;
    }

    public SubscriptionEntity() {
    }
}
