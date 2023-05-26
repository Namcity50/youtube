package com.example.youtube.dto.video;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class VideoPlaylistVideoInfoDTO {
    private String id;
    private AttachPlayListInfoDTO photo;
    private String title;
    private LocalDateTime duration;
}
