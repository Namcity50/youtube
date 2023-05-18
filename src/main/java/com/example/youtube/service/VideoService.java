package com.example.youtube.service;

import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.enums.VideoStatus;
import com.example.youtube.enums.VideoType;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.VideoRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;

    public VideoDTO create(VideoDTO dto) {
        VideoEntity entity = new VideoEntity();
        entity.setPreview_attach_id(dto.getPreview_attach_id());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setType(dto.getType());
        entity.setView_count(dto.getView_count());
        entity.setShared_count(dto.getShared_count());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(UUID.fromString(dto.getChannelId()));
        entity.setLike_count(dto.getLike_count());
        entity.setDislike_count(dto.getDislike_count());
        videoRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public VideoDTO update(String id, VideoDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        VideoEntity vId = get(id);
        if (!profileId.equals(vId)) {
            throw new ItemNotFoundException(" vioes not found!!!");
        }
        VideoEntity entity = get(id);
        entity.setPreview_attach_id(dto.getPreview_attach_id());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setType(VideoType.VIDEO);
        entity.setStatus(VideoStatus.PUBLIC);
        entity.setView_count(dto.getView_count());
        entity.setShared_count(dto.getShared_count());
        entity.setDescription(dto.getDescription());
        entity.setChannelId(UUID.fromString(dto.getChannelId()));
        entity.setLike_count(dto.getLike_count());
        entity.setDislike_count(dto.getDislike_count());
        videoRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public VideoDTO changeStatus(String id, VideoDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        VideoEntity vId = get(id);
        if (!profileId.equals(vId)) {
            throw new ItemNotFoundException(" videos not found!!!");
        }
        VideoEntity entity = new VideoEntity();
        entity.setStatus(VideoStatus.PUBLIC);
        videoRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public VideoEntity get(String id) {
        Optional<VideoEntity> optional = videoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("video not found");
        }
        return optional.get();
    }


    public VideoDTO IncreaseViewCount(String id, VideoDTO dto) {
        List<VideoEntity> list = videoRepository.findByIdAndViewCount(id);
        list.forEach(entity -> {
            entity.setPreview_attach_id(dto.getPreview_attach_id());
            entity.setTitle(dto.getTitle());
            entity.setCategoryId(dto.getCategoryId());
            entity.setAttachId(dto.getAttachId());
            entity.setType(VideoType.VIDEO);
            entity.setStatus(VideoStatus.PUBLIC);
            entity.setView_count(dto.getView_count());
            entity.setShared_count(dto.getShared_count());
            entity.setDescription(dto.getDescription());
            entity.setChannelId(UUID.fromString(dto.getChannelId()));
            entity.setLike_count(dto.getLike_count());
            entity.setDislike_count(dto.getDislike_count());
            videoRepository.save(entity);
            dto.setId(entity.getId());
        });
        return dto;
    }
}
