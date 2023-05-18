package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachPlayListInfoDTO;
import com.example.youtube.dto.channel.ChannelPlayListInfoDTO;
import com.example.youtube.dto.playList.PlayListInfoDTO;
import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.dto.profile.ProfilePlayListInfoDTO;
import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.PlayListEnums;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.mapper.PlayListMapper;
import com.example.youtube.repository.PlayListRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PlayListService {
    private final ProfileService profileService;
    private final PlayListRepository playListRepository;
    private final AttachService attachService;

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
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setProfileId(profileId);
        playListRepository.save(entity);
        return true;
    }

    public Boolean updateStatus(Integer id) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        PlayListEntity entity = getPlayListById(id);
        if (!entity.getProfileId().equals(profileId)) {
            throw new AppBadRequestException("Not found playList Owner");
        }
        if (entity.getStatus().equals(PlayListEnums.ROLE_PUBLIC)) {
            int i = playListRepository.updateStatus(id, PlayListEnums.ROLE_PRIVATE);
            return i > 0;
        } else if (entity.getStatus().equals(PlayListEnums.ROLE_PRIVATE)) {
            int a = playListRepository.updateStatus(id, PlayListEnums.ROLE_PUBLIC);
            return a > 0;
        }
        return false;
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
        return false;
    }

    public Page<PlayListInfoDTO> getPagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<PlayListMapper> entityPage = playListRepository.findByPlayListIdPagination(pageable);
        Long totalCount = entityPage.getTotalElements();
        List<PlayListMapper> entityList = entityPage.getContent();
        List<PlayListInfoDTO> dtoList = new LinkedList<>();
        entityList.forEach(entity -> {
            dtoList.add(getPlayListInfo(entity));
        });
        Page<PlayListInfoDTO> result = new PageImpl<>(dtoList, pageable, totalCount);
        return result;
    }

    public PlayListInfoDTO getPlayListInfo(PlayListMapper entity) {
        PlayListInfoDTO dto = new PlayListInfoDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setStatus(PlayListEnums.valueOf(entity.getStatus()));
        dto.setDescription(entity.getDescription());
        dto.setOrder_num(entity.getOrderNumber());
        dto.setChannel(new ChannelPlayListInfoDTO(entity.getChannelId(), entity.getChannelName(),
                new AttachPlayListInfoDTO(entity.getChannelId(),
                        attachService.getAttachLink(entity.getChanPhotoId()))));
        dto.setProfile(new ProfilePlayListInfoDTO(entity.getProfileId(), entity.getProfileName(),
                entity.getProfileSurname(),
                new AttachPlayListInfoDTO(entity.getProPhotoId(),
                        attachService.getAttachLink(entity.getProPhotoId()))));
        return dto;
    }
}
