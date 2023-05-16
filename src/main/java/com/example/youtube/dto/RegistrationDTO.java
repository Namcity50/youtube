package com.example.youtube.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private String name;
    private String surname;
    private String email;
    private AttachDTO attach;
    private String password;
}
