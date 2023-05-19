package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.dto.video.VideoShortInfoDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.dto.video.VideoUpdateDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.enums.VideoStatus;
import com.example.youtube.enums.VideoType;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.repository.VideoRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class VideoService {
    private final VideoRepository videoRepository;
    private final AttachService attachService;
    private final ChannelService channelService;

    public VideoDTO create(VideoDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity channel = channelService.getProfileChannel(profileId);
        //check
        if (videoRepository.getByAttachAndChannel(dto.getAttachId(), channel.getId()) != null) {
            throw new MethodNotAllowedException("Already exist video");
        }
        //
        VideoEntity entity = new VideoEntity();
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setAttachId(dto.getAttachId());
        entity.setType(VideoType.VIDEO);
        entity.setStatus(VideoStatus.PUBLIC);
        entity.setDescription(dto.getDescription());
        entity.setChannelId(channel.getId());
        entity.setViewCount(0);
        entity.setSharedCount(0);
        entity.setLikeCount(0);
        entity.setDislikeCount(0);
        entity = videoRepository.save(entity);
        return toDTO(entity);
    }

    private VideoDTO toDTO(VideoEntity entity) {

        VideoDTO videoDTO = new VideoDTO();
        videoDTO.setId(entity.getId());
        videoDTO.setTitle(entity.getTitle());
        videoDTO.setDescription(entity.getDescription());
        videoDTO.setPreviewAttachId(entity.getPreviewAttachId());
        videoDTO.setCategoryId(entity.getCategoryId());
        videoDTO.setAttachId(entity.getAttachId());
        videoDTO.setType(entity.getType());
        videoDTO.setViewCount(entity.getViewCount());
        videoDTO.setSharedCount(entity.getSharedCount());
        videoDTO.setDescription(entity.getDescription());
        videoDTO.setChannelId(entity.getChannelId());
        videoDTO.setLikeCount(entity.getLikeCount());
        videoDTO.setDislikeCount(entity.getDislikeCount());
        videoDTO.setStatus(entity.getStatus());
        return videoDTO;

    }

    public VideoDTO update(String id, VideoUpdateDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        VideoEntity entity = getById(id);
        if (entity.getChannel().getProfileId() != profileId) {
            throw new MethodNotAllowedException("This video belong to other profile");
        }
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
        entity.setDescription(dto.getDescription());
        entity = videoRepository.save(entity);

        return toDTO(entity);
    }


    public VideoDTO changeStatus(String id) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        VideoEntity videoEntity = getById(id);
        if (!profileId.equals(videoEntity.getChannel().getProfileId())) {
            throw new ItemNotFoundException(" This video belong to other profile");
        }
        VideoStatus status;
        if (videoEntity.getStatus().equals(VideoStatus.PRIVATE)) status = VideoStatus.PUBLIC;
        else status = VideoStatus.PRIVATE;
        videoRepository.changeStatus(status, videoEntity.getId());
        videoEntity.setStatus(status);
        return toDTO(videoEntity);
    }

    public VideoEntity getById(String id) {
        Optional<VideoEntity> optional = videoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Video not found");
        }
        return optional.get();
    }


    public int increaseViewCount(String videoId) {
        videoRepository.increaseViewCount(videoId);
        return videoRepository.getViewCount(videoId);
    }

    public Page<VideoShortInfoDTO> pagingByCategory(int page, int size, Integer id) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoEntity> videoEntityPage = videoRepository.findAllByCategoryId(id, pageable);
        long totalElements = videoEntityPage.getTotalElements();
        List<VideoEntity> contentList = videoEntityPage.getContent();
        List<VideoShortInfoDTO> list = new LinkedList<>();
        contentList.forEach(content -> {
            list.add(toVideoShortInfo(content));
        });
        return new PageImpl<>(list, pageable, totalElements);
    }

    private VideoShortInfoDTO toVideoShortInfo(VideoEntity entity) {
        VideoShortInfoDTO dto = new VideoShortInfoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        String attachId = entity.getPreviewAttachId();
        dto.setPreviewAttach(new AttachDTO(attachId, attachService.getAttachLink(attachId)));
        dto.setPublishedDate(entity.getPublishedDate());
        ChannelEntity channel = entity.getChannel();
        dto.setChannelDTO(new ChannelDTO(channel.getId(), channel.getName(), attachService.getAttachLink(channel.getPhotoId())));
        dto.setViewCount(entity.getViewCount());
        return dto;

    }


    public Object pagingByTitle(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoEntity> videoEntityPage = videoRepository.findAllByTitle("%"+text+"%", pageable);
        long totalElements = videoEntityPage.getTotalElements();
        List<VideoShortInfoDTO> list = new LinkedList<>();
        videoEntityPage.getContent().forEach(content -> {
            list.add(toVideoShortInfo(content));
        });
        return new PageImpl<>(list, pageable, totalElements);

    }
}
