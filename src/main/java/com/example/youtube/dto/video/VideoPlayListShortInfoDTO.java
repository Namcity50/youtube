package com.example.youtube.dto.video;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VideoPlayListShortInfoDTO {
     private String id;
     private String name;
     private String duration;
}
