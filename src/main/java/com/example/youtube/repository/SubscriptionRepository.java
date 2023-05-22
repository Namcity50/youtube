package com.example.youtube.repository;

import com.example.youtube.entity.SubscriptionEntity;
import com.example.youtube.enums.GeneralStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Integer> {
    @Query("from SubscriptionEntity where channelId=?1 and profileId=?2 and status=?3 ")
    SubscriptionEntity getByChannelIdAndProfileIdStatus(String channelId, Integer profileId, GeneralStatus status);
    @Query("from SubscriptionEntity where channelId=?1 and profileId=?2  ")
    SubscriptionEntity getByChannelIdAndProfileId(String channelId, Integer profileId);
    @Query("select new com.example.youtube.entity.SubscriptionEntity(s.id,s.channel,s.notification) from SubscriptionEntity as s where s.profileId=?1 and s.status='ROLE_ACTIVE'")
    Page<SubscriptionEntity> findAllByStatus(Integer profileId, Pageable pageable);
    @Query("select new com.example.youtube.entity.SubscriptionEntity(s.id,s.channel,s.createdDate,s.notification) from SubscriptionEntity as s where s.profileId=?1 and s.status='ROLE_ACTIVE'")
    Page<SubscriptionEntity> findAllByStatusAdmin(Integer profileId, Pageable pageable);
}
