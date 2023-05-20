package com.example.youtube.repository;
import com.example.youtube.entity.VideoTagEntity;
import com.example.youtube.mapper.VideoTagMapper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface VideoTagRepository extends CrudRepository<VideoTagEntity,Integer> {
    VideoTagEntity findByTagIdAndVideoId(Integer tagId, String videoId);
    @Query(value = " select t.id,v.video_id as videoId, " +
            " t.id as tagId, t.name as tagName, " +
            " v.created_date as createdDate from video_tag as v inner join tag as t on " +
            " t.id = v.tag_id ",nativeQuery = true)
    List<VideoTagMapper> findAllByVideoId(String id);
}
