package com.example.youtube.repository;

import com.example.youtube.entity.VideoEntity;
import com.example.youtube.enums.VideoStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VideoRepository extends CrudRepository<VideoEntity, String>, PagingAndSortingRepository<VideoEntity, String> {
    @Query(value = "select new com.example.youtube.entity.VideoEntity (v.id ,v.title,v.previewAttachId,v.publishedDate," +
            " new com.example.youtube.entity.ChannelEntity(v.channelId,v.channel.name,v.channel.photoId)," +
            "v.viewCount ) from VideoEntity as v where v.categoryId = ?1 ")
    Page<VideoEntity> findAllByCategoryId(Integer id, Pageable pageable);

    @Query("from VideoEntity where attachId = ?1 and channelId =?2 ")
    VideoEntity getByAttachAndChannel(String attachId, String channelId);

    @Modifying
    @Transactional
    @Query("update VideoEntity  set status=?1 where id=?2 ")
    int changeStatus(VideoStatus status, String videoId);

    @Modifying
    @Transactional
    @Query("update VideoEntity  set viewCount=viewCount+1 where id=?1 ")
    void increaseViewCount(String videoId);

    @Query(value = "select view_count from video where id = ?1", nativeQuery = true)
    int getViewCount(String videoId);

    @Query(value = "select new com.example.youtube.entity.VideoEntity (v.id ,v.title,v.previewAttachId,v.publishedDate," +
            " new com.example.youtube.entity.ChannelEntity(v.channelId,v.channel.name,v.channel.photoId)," +
            "v.viewCount ) from VideoEntity as v where v.title ilike ?1 ")
    Page<VideoEntity> findAllByTitle(String text, Pageable pageable);

    @Query(value = "select new com.example.youtube.entity.VideoEntity (v.id ,v.title,v.previewAttachId,v.publishedDate," +
            " new com.example.youtube.entity.ChannelEntity(v.channelId,v.channel.name,v.channel.photoId)," +
            "v.viewCount ) from VideoEntity as v inner join VideoTagEntity as vt on vt.videoId=v.id " +
            " where vt.tagId=?1")
    Page<VideoEntity> findByTagId(Integer tagId, Pageable pageable);

    @Query(value = "select new com.example.youtube.entity.VideoEntity (v.id ,v.title,v.previewAttachId,v.publishedDate," +
            " new com.example.youtube.entity.ChannelEntity(v.channelId,v.channel.name,v.channel.photoId)," +
            "v.viewCount ) from VideoEntity as v where v.id = ?1 ")
    Page<VideoEntity> getVideoById(Integer id);

    @Query(value = "select new com.example.youtube.entity.VideoEntity (v.id ,v.title,v.previewAttachId,v.publishedDate," +
            " new com.example.youtube.entity.ChannelEntity(v.channelId,v.channel.name,v.channel.photoId)," +
            "v.viewCount ) from VideoEntity as v  ")
    void getVideoListAdmin(Pageable pageable);
}
