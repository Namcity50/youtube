package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.video.VideoShortInfoDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.dto.video.VideoUpdateDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.enums.VideoStatus;
import com.example.youtube.enums.VideoType;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.mapper.VideoOwnerPlayListInfoDTO;
import com.example.youtube.mapper.VideoOwnerPlayListInfoMapper;
import com.example.youtube.mapper.VideoPlayListInfo;
import com.example.youtube.repository.VideoRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        dto.setPreviewAttach(new AttachDTO(attachId, attachService.getAttachByLink(attachId)));
        dto.setPublishedDate(entity.getPublishedDate());
        ChannelEntity channel = entity.getChannel();
        dto.setChannelDTO(new ChannelDTO(channel.getId(), channel.getName(), attachService.getAttachByLink(channel.getPhotoId())));
        dto.setViewCount(entity.getViewCount());
        return dto;

    }


    public Object pagingByTitle(int page, int size, String text) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoEntity> videoEntityPage = videoRepository.findAllByTitle("%" + text + "%", pageable);
        long totalElements = videoEntityPage.getTotalElements();
        List<VideoShortInfoDTO> list = new LinkedList<>();
        videoEntityPage.getContent().forEach(content -> {
            list.add(toVideoShortInfo(content));
        });
        return new PageImpl<>(list, pageable, totalElements);

    }

    public Object pagingByTag(int page, int size, Integer tagId) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<VideoEntity> videoEntityPage = videoRepository.findByTagId(tagId, pageable);
        List<VideoShortInfoDTO> list = new LinkedList<>();
        videoEntityPage.getContent().forEach(content -> {
            list.add(toVideoShortInfo(content));
        });
        return new PageImpl<>(list, pageable, videoEntityPage.getTotalElements());

    }

    public Object getVideoById(String videoId) {
        VideoEntity videoEntity = getById(videoId);
        if (videoEntity.getStatus() == VideoStatus.PRIVATE) {
            ////
        }
        return videoEntity;

    }

    public Page<VideoOwnerPlayListInfoDTO> getVideoListAdmin(int page, int size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<VideoOwnerPlayListInfoMapper> videoOwnerPlayListInfoMappers = videoRepository.getVideoListAdmin(pageable);
        List<VideoOwnerPlayListInfoDTO> dtoList = new ArrayList<>();
        videoOwnerPlayListInfoMappers.getContent().forEach(videoOwnerPlayListInfoMapper -> {
            dtoList.add(toVideoOwnerPlayList(videoOwnerPlayListInfoMapper));
        });
        return new PageImpl<>(dtoList, pageable, videoOwnerPlayListInfoMappers.getTotalElements());
    }

    private VideoOwnerPlayListInfoDTO toVideoOwnerPlayList(VideoOwnerPlayListInfoMapper videoOwnerPlayListInfoMapper) {
        VideoEntity videoEntity = videoOwnerPlayListInfoMapper.getVideo();
        VideoOwnerPlayListInfoDTO dto = new VideoOwnerPlayListInfoDTO();

        VideoShortInfoDTO videoShortInfoDTO = toVideoShortInfo(videoEntity);
        dto.setVideo(videoShortInfoDTO);

        PlaylistDTO playlistDTO = new PlaylistDTO();
        playlistDTO.setId(videoOwnerPlayListInfoMapper.getPlayList().getId());
        playlistDTO.setName(videoOwnerPlayListInfoMapper.getPlayList().getName());
        dto.setPlayList(playlistDTO);

        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(videoOwnerPlayListInfoMapper.getProfile().getId());
        profileDTO.setName(videoOwnerPlayListInfoMapper.getProfile().getName());
        profileDTO.setPhoto(attachService.getAttachByLink(videoOwnerPlayListInfoMapper.getProfile().getPhotoId()));

        dto.setProfile(profileDTO);
        return dto;
    }

    public Object getChannelVideoList(int page, int size, String channelId) {
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<VideoPlayListInfo> pageObj = videoRepository.getChannelVideoList(channelId, pageable);
        List<VideoPlayListInfo> infoList = pageObj.getContent();
        infoList.forEach(videoPlayListInfo -> {
            videoPlayListInfo.setPreviewAttachLink(attachService.getAttachByLink(videoPlayListInfo.getId()));
        });
        return new PageImpl<>(infoList, pageable, pageObj.getTotalElements());
    }
}
