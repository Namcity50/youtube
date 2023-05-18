package com.example.youtube.service;

import com.example.youtube.dto.video.VideoTagDTO;
import com.example.youtube.entity.VideoTagEntity;
import com.example.youtube.repository.VideoTagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
}
