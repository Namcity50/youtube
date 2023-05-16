package com.example.youtube.dto.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileResponseDTO {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private String photoUrl;
}
