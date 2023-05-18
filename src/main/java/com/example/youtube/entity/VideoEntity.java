package com.example.youtube.entity;

import com.example.youtube.enums.VideoStatus;
import com.example.youtube.enums.VideoType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "video")
public class VideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "preview_attach_id")
    private Integer preview_attach_id;
    @Column(name = "title")
    private String title;
    @Column(name = "category_id")
    private Integer categoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id",insertable = false,updatable = false)
    private CategoryEntity category;
    @Column(name = "attach_id")
    private String attachId;
    @Column(name = "created_date")
    private LocalDateTime created_date = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime published_date;
    @Column(name = "type")
    private VideoType type;
    @Column(name = "view_count")
    private Integer view_count;
    @Column(name = "shared_count")
    private Integer shared_count;
    @Column(name = "description")
    private String description;
    @Column(name = "channel_id")
    private UUID channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id",insertable = false,updatable = false)
    private ChannelEntity channel;
    @Column(name = "like_count")
    private Integer like_count;
    @Column(name = "dislike_count")
    private Integer dislike_count;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VideoStatus status;
    @Column(name = "profile_id")
    private Integer profileId;

}
