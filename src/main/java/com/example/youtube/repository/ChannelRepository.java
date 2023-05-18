package com.example.youtube.repository;

import com.example.youtube.entity.ChannelEntity;
import com.example.youtube.enums.GeneralStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface ChannelRepository extends JpaRepository<ChannelEntity,UUID> {
    ChannelEntity findByProfileIdAndStatus(Integer profileId,GeneralStatus status);
    @Modifying
    @Transactional
    @Query("update ChannelEntity set photoId=?1  where profileId = ?2 ")
    void updatePhoto(String photoId, Integer profileId);
    @Modifying
    @Transactional
    @Query("update ChannelEntity set bannerId=?1  where profileId = ?2 ")
    void updateBanner(String bannerId, Integer profileId);
    @Modifying
    @Transactional
    @Query("update ChannelEntity set status=?1  where id = ?2 ")
    void updateStatus(GeneralStatus channelStatus, UUID channelId);
    Optional<ChannelEntity> findByIdAndStatus(UUID id, GeneralStatus status);
}
