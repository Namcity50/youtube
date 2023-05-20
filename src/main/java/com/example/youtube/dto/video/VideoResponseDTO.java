package com.example.youtube.dto.video;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoResponseDTO {
    private String id;
    private String previewAttachId;
    private String title;
    private String description;



    public VideoResponseDTO(Integer id, Object previewAttachId, Object title) {
    }
}
