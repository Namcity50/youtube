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
    private String previewAttachId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "preview_attach_id", insertable = false, updatable = false)
    private AttachEntity previewAttach;
    @Column(name = "title")
    private String title;

    @Column(name = "category_id")
    private Integer categoryId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", insertable = false, updatable = false)
    private CategoryEntity category;

    @Column(name = "attach_id")
    private String attachId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attach_id", insertable = false, updatable = false)
    private AttachEntity attach;

    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "published_date")
    private LocalDateTime publishedDate = LocalDateTime.now();
    @Column(name = "type")
    private VideoType type;
    @Column(name = "view_count")
    private Integer viewCount;
    @Column(name = "shared_count")
    private Integer sharedCount;
    @Column(name = "description")
    private String description;
    @Column(name = "channel_id")
    private String channelId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    private ChannelEntity channel;
    @Column(name = "like_count")
    private Integer likeCount;
    @Column(name = "dislike_count")
    private Integer dislikeCount;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private VideoStatus status;

    public VideoEntity() {
    }

    public VideoEntity(String id, String title, String previewAttachId, LocalDateTime publishedDate, ChannelEntity channel, Integer viewCount) {
        this.id = id;
        this.publishedDate = publishedDate;
        this.previewAttachId = previewAttachId;
        this.title = title;
        this.viewCount = viewCount;
        this.channel = channel;
    }

    public VideoEntity(String id, String title, String previewAttachId, LocalDateTime publishedDate, Integer viewCount) {
        this.id = id;
        this.previewAttachId = previewAttachId;
        this.title = title;
        this.publishedDate = publishedDate;
        this.viewCount = viewCount;
    }
}
