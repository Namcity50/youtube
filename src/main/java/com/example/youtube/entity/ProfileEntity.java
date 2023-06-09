package com.example.youtube.entity;

import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GeneralStatus status;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ProfileRole role;
    @Column(name = "visible")
    private Boolean visible = Boolean.TRUE;
    @Column(name = "created_date")
    private LocalDateTime createdDate = LocalDateTime.now();
    // photo_id
    @OneToOne
    @JoinColumn(name = "photo_id",insertable = false,updatable = false)
    private AttachEntity attachEntity;

    @Column(name = "photo_id")
    private String photoId;

    public ProfileEntity(Integer id, String name, String photoId) {
        this.id = id;
        this.name = name;
        this.photoId = photoId;
    }

    public ProfileEntity() {
    }
}
