package com.example.youtube.repository;

import com.example.youtube.entity.ChannelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<ChannelEntity,String> {
    ChannelEntity findByProfileId(Integer profileId);
}
