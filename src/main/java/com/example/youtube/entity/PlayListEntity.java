package com.example.youtube.entity;

import com.example.youtube.enums.PlayListEnums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "playList")
public class PlayListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "channel_id")
    private String channelId;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private PlayListEnums status;
    @Column(name = "order_number")
    private Integer orderNumber;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
}
