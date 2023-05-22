package com.example.youtube.repository;

import com.example.youtube.entity.SubscriptionEntity;
import com.example.youtube.enums.GeneralStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    @Query("from SubscriptionEntity where channelId=?1 and profileId=?2 and status=?3 ")
    SubscriptionEntity getByChannelIdAndProfileIdStatus(String channelId, Integer profileId, GeneralStatus status);
    @Query("from SubscriptionEntity where channelId=?1 and profileId=?2  ")
    SubscriptionEntity getByChannelIdAndProfileId(String channelId, Integer profileId);
}
