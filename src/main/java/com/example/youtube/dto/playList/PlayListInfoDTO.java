package com.example.youtube.dto.playList;

import com.example.youtube.enums.PlayListEnums;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayListInfoDTO {
     private Integer id;
     private String name;
     private String description;
     private PlayListEnums status; //(private, public)
     private Integer order_num;
//     private channel(id,name,photo(id,url), profile(id,name,surname,photo(id,url)))
}
