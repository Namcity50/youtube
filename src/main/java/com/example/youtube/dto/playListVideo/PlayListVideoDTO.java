package com.example.youtube.dto.playListVideo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListVideoDTO {
    private Integer id;
    private Integer playlistId;
    private String videoId;
    private LocalDateTime createdDate;
    private Integer orderNum;
}
