package com.example.youtube.repository.tag;

import com.example.youtube.entity.tag.VideoTagEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoTagRepository extends CrudRepository<VideoTagEntity, Integer> {

//    @Query("select  v.ownerId from VideoEntity v where  v.id=?1")
//    Integer getOwnerId(String videoId);

    @Transactional
    long deleteByVideoIdAndTagId(String videoId, Integer tagId);


    List<VideoTagEntity> findByVideoId(String videoId);
}