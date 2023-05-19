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
    private Integer profile_id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "video_id",updatable = false,insertable = false)
    private VideoEntity video;
    @Column(name = "video_id")
    private String video_id;
    @Column(name = "content")
    private String content;
    @Column(name = "reply_id")
    private Integer reply_id;
    @Column(name = "like_count")
    private Integer like_count;
    @Column(name = "dislike_count")
    private Integer dislike_count;
    @Column(name = "created_date")
    private LocalDateTime created_date;

}
