package com.example.youtube.dto.channel;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelPlayListInfoDTO {
    private String id;
    private String name;
    private AttachPlayListInfoDTO photo;
}
