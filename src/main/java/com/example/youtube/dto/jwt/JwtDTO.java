package com.example.youtube.dto.jwt;
import com.example.youtube.enums.ProfileRole;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtDTO {
    private String mail;
    private ProfileRole role;

}
