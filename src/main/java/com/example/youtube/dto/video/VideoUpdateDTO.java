package com.example.youtube.dto.video;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VideoUpdateDTO {
    private String previewAttachId;
    private String title;
    private Integer categoryId;

    private String description;

}
