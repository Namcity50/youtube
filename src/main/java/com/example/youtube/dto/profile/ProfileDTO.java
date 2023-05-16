package com.example.youtube.dto.profile;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
