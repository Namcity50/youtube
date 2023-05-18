package com.example.youtube.dto.video;

import com.example.youtube.dto.attach.AttachDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class VideShortInfoDTO {
    private String id;
    private String title;
    private AttachDTO preview_attach;      //(id, url),
    private LocalDateTime published_date;

}
