package com.example.youtube.dto;

import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter

public class ProfileDTO {
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String photo;
    private ProfileRole role;
    private GeneralStatus status;
}
