package com.example.youtube.dto.profile;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfilePlayListInfoDTO {
    private Integer id;
    private String name;
    private String surname;
    private AttachPlayListInfoDTO photo;
}
