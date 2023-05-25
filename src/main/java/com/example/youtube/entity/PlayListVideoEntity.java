package com.example.youtube.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "playListVideo")
public class PlayListVideoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne()
    @JoinColumn(name = "playlist_id",insertable = false,updatable = false)
    private PlayListEntity playList;
    @Column(name = "playlist_id")
    private Integer playlistId;
    @ManyToOne()
    @JoinColumn(name = "video_id",insertable = false,updatable = false)
    private VideoEntity video;
    @Column(name = "video_id")
    private String videoId;
    @Column(name = "oder_number")
    private Integer orderNum;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();

}
