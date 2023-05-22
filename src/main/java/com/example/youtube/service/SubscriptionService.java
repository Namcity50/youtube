package com.example.youtube.service;

import com.example.youtube.dto.subscription.SubscriptionCreatDTO;
import com.example.youtube.dto.subscription.SubscriptionDTO;
import com.example.youtube.dto.subscription.SubscriptionInfoDTO;
import com.example.youtube.dto.subscription.SubscriptionUpdateDTO;
import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.entity.SubscriptionEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.SubscriptionRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SubscriptionService {
    private SubscriptionRepository subscriptionRepository;
    private AttachService attachService;


    public Object create(SubscriptionCreatDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        SubscriptionEntity subscriptionEntity = getByChannelIdAndProfileId(dto.getChannelId(), profileId);
        if (subscriptionEntity.getStatus().equals(GeneralStatus.ROLE_ACTIVE)) {
            throw new AppBadRequestException("You already subscribed this channel");
        } else if (subscriptionEntity.getStatus().equals(GeneralStatus.ROLE_BLOCK)) {
            subscriptionEntity.setStatus(GeneralStatus.ROLE_ACTIVE);
            subscriptionRepository.save(subscriptionEntity);
        } else {
            SubscriptionEntity subscription = new SubscriptionEntity();
            subscription.setProfileId(profileId);
            subscription.setCreatedDate(LocalDateTime.now());
            subscription.setStatus(GeneralStatus.ROLE_ACTIVE);
            subscription.setNotification(dto.getNotificationType());
            subscription.setChannelId(dto.getChannelId());
            subscriptionRepository.save(subscription);
            return toDTO(subscription);
        }
        return toDTO(subscriptionEntity);

    }

    private SubscriptionDTO toDTO(SubscriptionEntity subscription) {
        SubscriptionDTO dto = new SubscriptionDTO();
        dto.setId(subscription.getId());
        dto.setStatus(subscription.getStatus());
        dto.setChannelId(subscription.getChannelId());
        dto.setUnSubscribeDate(subscription.getUnSubscribeDate());
        dto.setProfileId(subscription.getProfileId());
        dto.setNotification(subscription.getNotification());
        dto.setCreatedDate(subscription.getCreatedDate());
        return dto;
    }


    private SubscriptionEntity getByChannelIdAndProfileIdStatus(String channelId, Integer profileId, GeneralStatus status) {
        return subscriptionRepository.getByChannelIdAndProfileIdStatus(channelId, profileId, status);
    }

    private SubscriptionEntity getByChannelIdAndProfileId(String channelId, Integer profileId) {
        return subscriptionRepository.getByChannelIdAndProfileId(channelId, profileId);
    }

    public Object changeStatus(SubscriptionUpdateDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        SubscriptionEntity entity = getByChannelIdAndProfileId(dto.getChannelId(), profileId);
        entity.setStatus(dto.getStatus());
        if (dto.getStatus().equals(GeneralStatus.ROLE_BLOCK)) {
            entity.setUnSubscribeDate(LocalDateTime.now());
        }
        subscriptionRepository.save(entity);
        return toDTO(entity);
    }

    public Object changeNotification(SubscriptionUpdateDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        SubscriptionEntity entity = getByChannelIdAndProfileId(dto.getChannelId(), profileId);
        if (entity == null) {
            throw new ItemNotFoundException("Item not found exception");
        }
        entity.setNotification(dto.getNotificationType());
        subscriptionRepository.save(entity);
        return toDTO(entity);
    }

    public Page<SubscriptionInfoDTO> list(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Integer profileId = SpringSecurityUtil.getProfileId();
        Page<SubscriptionEntity> pageObj = subscriptionRepository.findAllByStatus(profileId, pageable);
        return new PageImpl<>(toSubscriptionInfoDTO(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    private List<SubscriptionInfoDTO> toSubscriptionInfoDTO(List<SubscriptionEntity> content) {
        List<SubscriptionInfoDTO> dtoList = new ArrayList<>();
        content.forEach(subscriptionEntity -> {
            SubscriptionInfoDTO infoDTO = new SubscriptionInfoDTO();
            infoDTO.setId(subscriptionEntity.getId());
            infoDTO.setNotificationType(subscriptionEntity.getNotification());
            ChannelDTO channelDTO = new ChannelDTO();
            ChannelEntity channel = subscriptionEntity.getChannel();
            channelDTO.setId(channel.getId());
            channelDTO.setName(channel.getName());
            channelDTO.setPhotoId(attachService.getAttachByLink(channel.getPhotoId()));
            infoDTO.setChannelDTO(channelDTO);
            dtoList.add(infoDTO);
        });
        return dtoList;
    }

    public Object adminList(Integer profileId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page-1, size);
        Page<SubscriptionEntity> pageObj = subscriptionRepository.findAllByStatusAdmin(profileId, pageable);
        return new PageImpl<>(toSubscriptionInfoDTOAdmin(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }
    private List<SubscriptionInfoDTO> toSubscriptionInfoDTOAdmin(List<SubscriptionEntity> content) {
        List<SubscriptionInfoDTO> dtoList = new ArrayList<>();
        content.forEach(subscriptionEntity -> {
            SubscriptionInfoDTO infoDTO = new SubscriptionInfoDTO();
            infoDTO.setId(subscriptionEntity.getId());
            infoDTO.setNotificationType(subscriptionEntity.getNotification());
            ChannelDTO channelDTO = new ChannelDTO();
            ChannelEntity channel = subscriptionEntity.getChannel();
            channelDTO.setId(channel.getId());
            channelDTO.setName(channel.getName());
            channelDTO.setPhotoId(attachService.getAttachByLink(channel.getPhotoId()));
            infoDTO.setChannelDTO(channelDTO);
            infoDTO.setCreatedDate(subscriptionEntity.getCreatedDate());
            dtoList.add(infoDTO);
        });
        return dtoList;
    }
}
