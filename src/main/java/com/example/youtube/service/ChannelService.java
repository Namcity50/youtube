package com.example.youtube.service;

import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.repository.ChannelRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final AttachService attachService;
    private final ProfileService profileService;

    public ChannelDTO create(ChannelDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity oldChannel = channelRepository.findByProfileIdAndStatus(profileId,GeneralStatus.ROLE_ACTIVE);
        if (oldChannel != null) {
            throw new MethodNotAllowedException("Method not allowed .One user create only one channel");
        }
        //to entity
        ChannelEntity entity = new ChannelEntity();
        entity.setName(dto.getName());
        entity.setPhotoId(dto.getPhotoId());
        entity.setBannerId(dto.getBannerId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(GeneralStatus.ROLE_ACTIVE);
        entity.setProfileId(SpringSecurityUtil.getProfileId());
        //save entity
        channelRepository.save(entity);
        //return dto
        dto.setId(entity.getId().toString());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    public ChannelEntity getProfileChannel(Integer profileId) {
        ChannelEntity entity = channelRepository.findByProfileIdAndStatus(profileId, GeneralStatus.ROLE_ACTIVE);
        if (entity == null) {
            throw new ItemNotFoundException("Channel not found");
        }
        return entity;
    }

    public ChannelDTO updatePhoto(String photoId) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity channel = getProfileChannel(profileId);
        String oldPhoto = channel.getPhotoId();
        // update photo
        channelRepository.updatePhoto(photoId, profileId);
        //delete
        if (oldPhoto != null) {
            attachService.delete(oldPhoto);
        }
        //return
        channel.setPhotoId(photoId);
        return toDTO(channel);
    }

    private ChannelDTO toDTO(ChannelEntity channel) {
        ChannelDTO dto = new ChannelDTO();
        dto.setId(channel.getId().toString());
        dto.setName(channel.getName());
        dto.setDescription(channel.getDescription());
        dto.setPhotoId(channel.getPhotoId());
        dto.setBannerId(channel.getBannerId());
        dto.setStatus(channel.getStatus());
        dto.setProfileId(channel.getProfileId());
        return dto;
    }


    public Object updateBanner(String bannerId) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity channel = getProfileChannel(profileId);
        String oldBanner = channel.getBannerId();
        // update photo
        channelRepository.updateBanner(bannerId, profileId);
        //delete
        if (oldBanner != null) {
            attachService.delete(oldBanner);
        }
        //return
        channel.setBannerId(bannerId);
        return toDTO(channel);
    }

    public Page<ChannelDTO> pagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "name"));
        Page<ChannelEntity> entityPage = channelRepository.findAll(pageable);
        return new PageImpl<>(toList(entityPage.getContent()), pageable, entityPage.getTotalElements());
    }

    private List<ChannelDTO> toList(List<ChannelEntity> entityList) {
        List<ChannelDTO> dtoList = new ArrayList<>();
        entityList.forEach(channelEntity -> {
            dtoList.add(toDTO(channelEntity));
        });
        return dtoList;
    }

    public ChannelEntity getByIdAndStatus(String id) {
        return channelRepository.findByIdAndStatus(id, GeneralStatus.ROLE_ACTIVE).orElseThrow(() -> {
            throw new ItemNotFoundException("Channel not found");
        });
    }
    public ChannelEntity getById(String id) {
        return channelRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Channel not found");
        });
    }

    public ChannelDTO getChannelById(String id) {
        return toDTO(getByIdAndStatus(id));
    }

    public ChannelDTO changeStatus(String channelId) {
        ChannelEntity channel = getById(channelId);
        GeneralStatus channelStatus;
        if (channel.getStatus().equals(GeneralStatus.ROLE_ACTIVE)) channelStatus = GeneralStatus.ROLE_ADMIN_BLOCK;
        else channelStatus = GeneralStatus.ROLE_ACTIVE;
        channelRepository.updateStatus(channelStatus, UUID.fromString(channelId));
        //return
        channel.setStatus(channelStatus);
        return toDTO(channel);
    }

    public ChannelDTO getChannel() {
        Integer pId = SpringSecurityUtil.getProfileId();
        return toDTO(getProfileChannel(pId));
    }

    public ChannelDTO update(ChannelDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity channel = getProfileChannel(profileId);

        channel.setPhotoId(dto.getPhotoId());
        channel.setBannerId(dto.getBannerId());
        channel.setName(dto.getName());
        channel.setDescription(dto.getDescription());
        channelRepository.save(channel);
        return toDTO(channel);
    }
}
