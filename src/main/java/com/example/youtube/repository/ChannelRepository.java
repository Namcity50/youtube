package com.example.youtube.repository;

import com.example.youtube.entity.ChannelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ChannelRepository extends JpaRepository<ChannelEntity,String> {
    ChannelEntity findByProfileId(Integer profileId);
    @Modifying
    @Transactional
    @Query("update ChannelEntity set photoId=?1  where profileId = ?2 ")
    void updatePhoto(String photoId, Integer profileId);
    @Modifying
    @Transactional
    @Query("update ChannelEntity set bannerId=?1  where profileId = ?2 ")
    void updateBanner(String bannerId, Integer profileId);
}
