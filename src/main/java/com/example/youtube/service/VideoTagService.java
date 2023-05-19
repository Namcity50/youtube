package com.example.youtube.service;

import com.example.youtube.dto.tag.TagResponseDTO;
import com.example.youtube.dto.video.VideoTagDTO;
import com.example.youtube.dto.video.VideoTagResponseDTO;
import com.example.youtube.entity.VideoTagEntity;
import com.example.youtube.mapper.VideoTagMapper;
import com.example.youtube.repository.VideoTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class VideoTagService {
    private final VideoTagRepository videoTagRepository;

    public Boolean create(VideoTagDTO dto) {
        VideoTagEntity entity = new VideoTagEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setTagId(dto.getTagId());
        videoTagRepository.save(entity);
        dto.setId(entity.getId());
        return true;
    }

    public Boolean delete(Integer tagId, String videoId) {
        VideoTagEntity entity = videoTagRepository.findByTagIdAndVideoId(tagId, videoId);
        videoTagRepository.delete(entity);
        return true;
    }

    public List<VideoTagResponseDTO> getByVideoTagList(String id) {
        List<VideoTagMapper> mapperList = videoTagRepository.findAllByVideoId(id);
        List<VideoTagResponseDTO> dtoList = new LinkedList<>();
        mapperList.forEach(mapper -> {
            dtoList.add(getByVideoTAgResponse(mapper));
        });
        return dtoList;
    }
    public VideoTagResponseDTO getByVideoTAgResponse(VideoTagMapper mapper ){
        VideoTagResponseDTO dto = new VideoTagResponseDTO();
        dto.setId(mapper.getId());
        dto.setVideoId(mapper.getVideoId());
        dto.setTag(new TagResponseDTO(mapper.getTagId(),mapper.getTagName()));
        dto.setCreatedDate(mapper.getCreatedDate());
        return dto;
    }
}
