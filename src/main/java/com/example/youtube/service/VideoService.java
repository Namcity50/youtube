package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.video.VideShortInfoDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.ProfileEntity;
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
import java.util.UUID;

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
        entity.setType(dto.getType());
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
        return videoDTO;

    }

    public VideoDTO update(String id, VideoDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        VideoEntity entity = getById(id);
        if (entity.getChannel().getProfileId() != profileId) {
            throw new MethodNotAllowedException("This video belong to other profile");
        }
        entity.setPreviewAttachId(dto.getPreviewAttachId());
        entity.setTitle(dto.getTitle());
        entity.setCategoryId(dto.getCategoryId());
//        entity.setType(VideoType.VIDEO);
        entity.setStatus(VideoStatus.PUBLIC);
        entity.setViewCount(dto.getViewCount());
        entity.setSharedCount(dto.getSharedCount());
        entity.setDescription(dto.getDescription());
//        entity.setChannelId((dto.getChannelId()));
        entity.setLikeCount(dto.getLikeCount());
        entity.setDislikeCount(dto.getDislikeCount());
        videoRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    //
//    public VideoDTO changeStatus(String id, VideoDTO dto) {
//        Integer profileId = SpringSecurityUtil.getProfileId();
//        VideoEntity vId = get(id);
//        if (!profileId.equals(vId)) {
//            throw new ItemNotFoundException(" videos not found!!!");
//        }
//        VideoEntity entity = new VideoEntity();
//        entity.setStatus(VideoStatus.PUBLIC);
//        videoRepository.save(entity);
//        dto.setId(entity.getId());
//        return dto;
//    }
//
    public VideoEntity getById(String id) {
        Optional<VideoEntity> optional = videoRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Video not found");
        }
        return optional.get();
    }
//
//
//    public VideoDTO IncreaseViewCount(String id, VideoDTO dto) {
//        List<VideoEntity> list = videoRepository.findByIdAndViewCount(id);
//        list.forEach(entity -> {
//            entity.setPreviewAttachId(dto.getPreview_attach_id());
//            entity.setTitle(dto.getTitle());
//            entity.setCategoryId(dto.getCategoryId());
//            entity.setAttachId(dto.getAttachId());
//            entity.setType(VideoType.VIDEO);
//            entity.setStatus(VideoStatus.PUBLIC);
//            entity.setViewCount(dto.getView_count());
//            entity.setSharedCount(dto.getShared_count());
//            entity.setDescription(dto.getDescription());
//            entity.setChannelId((dto.getChannelId()));
//            entity.setLikeCount(dto.getLike_count());
//            entity.setDislikeCount(dto.getDislike_count());
//            videoRepository.save(entity);
//            dto.setId(entity.getId());
//        });
//        return dto;
//    }
//
//    public Page<VideShortInfoDTO> getArticleByCategoryIdPaging(int page, int size, Integer id) {
//        Pageable pageable = PageRequest.of(page - 1, size);
//        Page<VideoEntity> videoEntityPage = videoRepository.findAllByCategoryId(pageable, id);
//
//        long totalElements = videoEntityPage.getTotalElements();
//        List<VideoEntity> contentList = videoEntityPage.getContent();
//        List<VideShortInfoDTO> list = new LinkedList<>();
//      contentList.forEach(content->{
//          list.add(toVideoShortInfo(content));
//      });
//      return new PageImpl<>(list,pageable,totalElements);
//    }
//
//
//    public VideShortInfoDTO toVideoShortInfo(VideoEntity entity) {
//        VideShortInfoDTO dto = new VideShortInfoDTO();
//        dto.setId(entity.getId());
//        dto.setTitle(entity.getTitle());
//        dto.setPreview_attach(attachService.getAttachLink(entity.getAttachId()));
//        return dto;
//    }
}
