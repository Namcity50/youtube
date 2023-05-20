package com.example.youtube.dto.playList;

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
public class PlayListResponseDTO {
    private Integer id;
    private String name;
    private Integer videCount;
    private Integer viewCount;
    private LocalDateTime updatedDate;
}
