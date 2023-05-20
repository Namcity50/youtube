package com.example.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id",updatable = false,insertable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id",updatable = false,insertable = false)
    private VideoEntity video;
    @Column(name = "video_id")
    private String videoId;
    @Column(name = "content")
    private String content;
    @Column(name = "reply_id")
    private Integer replyId;
    @Column(name = "like_count")
    private Integer like_count;
    @Column(name = "dislike_count")
    private Integer dislike_count;
    @Column(name = "created_date")
    private LocalDateTime created_date;

}
