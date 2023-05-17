package com.example.youtube.entity;

import com.example.youtube.enums.GeneralStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "channel")
public class ChannelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description", columnDefinition = " text ")
    private String description;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatus status;
    @OneToOne
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    private AttachEntity photo;
    @Column(name = "photo_id")
    private String photoId;
    @OneToOne
    @JoinColumn(name = "banner_id",insertable = false,updatable = false)
    private AttachEntity banner;
    @Column(name = "banner_id")
    private String bannerId;
    @OneToOne
    @JoinColumn(name = "profile_id",insertable = false,updatable = false)
    private ProfileEntity profile;
    @Column(name = "profile_id")
    private Integer profileId;

}
