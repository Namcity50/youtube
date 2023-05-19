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
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends CrudRepository<VideoEntity, String>, PagingAndSortingRepository<VideoEntity, String> {

    @Query("update VideoEntity set viewCount=:view_count+1 where id=:id")
    List<VideoEntity> findByIdAndViewCount(@Param("id") String id);


    Page<VideoEntity> findAllByCategoryId(Pageable pageable, Integer id);

    @Query("from VideoEntity where attachId = ?1 and channelId =?2 ")
    VideoEntity getByAttachAndChannel(String attachId, String channelId);

    @Modifying
    @Transactional
    @Query("update VideoEntity  set status=?1 where id=?2 ")
    int changeStatus(VideoStatus status, String videoId);

}
