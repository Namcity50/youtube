package com.example.youtube.service;

import com.example.youtube.dto.playList.PlayListInfoDTO;
import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.PlayListRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayListService {
    private final ProfileService profileService;
    private final PlayListRepository playListRepository;

    private PlayListEntity getPlayListById(Integer id) {
        Optional<PlayListEntity> optional = playListRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Not found playlist");
        }
        PlayListEntity entity = optional.get();
        return entity;
    }

    public PlaylistDTO create(PlaylistDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profileEntity = profileService.getByProfileId(profileId);
        PlayListEntity entity = new PlayListEntity();
        entity.setName(dto.getName());
        entity.setChannelId(dto.getChannelId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setProfileId(profileEntity.getId());
        playListRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, PlaylistDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        PlayListEntity entity = getPlayListById(id);
        if (!entity.getProfileId().equals(profileId)) {
            throw new AppBadRequestException("Not found playList Owner");
        }
        entity.setName(dto.getName());
        entity.setChannelId(dto.getChannelId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playListRepository.save(entity);
        return true;
    }


    public Boolean updateStatus(Integer id, String status) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        PlayListEntity entity = getPlayListById(id);
        if (!entity.getProfileId().equals(profileId)) {
            throw new AppBadRequestException("Not found playList Owner");
        }
        int i = playListRepository.updateStatus(id, status);
        return i > 0;
    }

    public Boolean delete(Integer id) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profile = profileService.getByProfileId(profileId);
        PlayListEntity entity = getPlayListById(id);
        if (profile.getRole().equals(ProfileRole.ROLE_ADMIN)) { // admin
            playListRepository.delete(entity);
            return true;
        } else if (entity.getProfileId().equals(profile.getId())) { // owner
            playListRepository.delete(entity);
            return true;
        }
        return true;
    }

    public Page<PlayListInfoDTO> getPagination(Integer page, Integer size) {
        return null;
    }
}
