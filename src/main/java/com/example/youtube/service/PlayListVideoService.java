package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.example.youtube.dto.channel.ChannelPlayListShortInfoDTO;
import com.example.youtube.dto.playListVideo.PlayListVideoDTO;
import com.example.youtube.dto.playListVideo.PlaylistVideoInfoDTO;
import com.example.youtube.dto.video.VideoPlaylistVideoInfoDTO;
import com.example.youtube.dto.video.VideoResponseDTO;
import com.example.youtube.entity.PlayListVideoEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.mapper.PlaylistVideoInfoMapper;
import com.example.youtube.repository.PlayListVideoRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PlayListVideoService {
    private final PlayListVideoRepository playListVideoRepository;
    private final AttachService attachService;
    private final ProfileService profileService;
    public PlayListVideoDTO create(PlayListVideoDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        log.info("PlayListVideo User owner not found: " + profileId );
        profileService.getByProfileId(profileId);
        PlayListVideoEntity entity = new PlayListVideoEntity();
        entity.setVideoId(dto.getVideoId());
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setOrderNum(dto.getOrderNum());
        playListVideoRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer pid, String vid, PlayListVideoDTO dto) {
        PlayListVideoEntity entity = playListVideoRepository.findByPlayListIdAndVideoId(pid,vid);
        entity.setPlaylistId(dto.getPlaylistId());
        entity.setVideoId(dto.getVideoId());
        entity.setOrderNum(dto.getOrderNum());
        playListVideoRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer pid, String vid) {
        PlayListVideoEntity entity = playListVideoRepository.findByPlayListIdAndVideoId(pid,vid);
        if (entity == null){
            throw new ItemNotFoundException("Not found PlayListVideoEntity :");
        }
        playListVideoRepository.delete(entity);
        return true;
    }

    public List<PlaylistVideoInfoDTO> getByList(Integer playListId) {
        List<PlaylistVideoInfoMapper> mappers = playListVideoRepository.findByPlayListId(playListId);
        List<PlaylistVideoInfoDTO> dtoList = new LinkedList<>();
        mappers.forEach(mapper -> {
            dtoList.add(getPlaylistVideoInfo(mapper));
        });
        return dtoList;
    }

    public PlaylistVideoInfoDTO getPlaylistVideoInfo(PlaylistVideoInfoMapper mapper ){
        PlaylistVideoInfoDTO dto = new PlaylistVideoInfoDTO();
        dto.setPlaylistId(mapper.getPlayListId());
        dto.setVideo(new VideoPlaylistVideoInfoDTO(mapper.getVideoId(),
                new AttachPlayListInfoDTO(mapper.getPreviewId(), attachService.getAttachByLink(mapper.getPreviewId())),
                mapper.getVideoTitle(),mapper.getVideoDuration()));
        dto.setChannel(new ChannelPlayListShortInfoDTO(mapper.getChannelId(), mapper.getChannelName()));
        dto.setOrderNum(mapper.getOrderNum());
        dto.setCreatedDate(mapper.getCreatedDate());
        return dto;
    }
}
