package com.example.youtube.repository;

import com.example.youtube.entity.VideoTagEntity;
import org.springframework.data.repository.CrudRepository;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity,Integer> {
    VideoTagEntity findByTagIdAndVideoId(Integer tagId, String videoId);
}
