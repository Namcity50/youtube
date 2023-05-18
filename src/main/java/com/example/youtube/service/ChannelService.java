package com.example.youtube.service;

import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.repository.ChannelRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChannelService {
    private final ChannelRepository channelRepository;
    private final AttachService attachService;

    public ChannelDTO create(ChannelDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ChannelEntity oldChannel = getProfileChannel(profileId);
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

    private ChannelEntity getProfileChannel(Integer profileId) {

        ChannelEntity entity = channelRepository.findByProfileId(profileId);
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
        channelRepository.updatePhoto(photoId,profileId);
        //delete
        if (oldPhoto!=null){
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
        channelRepository.updateBanner(bannerId,profileId);
        //delete
        if (oldBanner!=null){
            attachService.delete(oldBanner);
        }
        //return
        channel.setBannerId(bannerId);
        return toDTO(channel);
    }
}
