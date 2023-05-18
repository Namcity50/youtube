package com.example.youtube.repository;

import com.example.youtube.entity.VideoEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends CrudRepository<VideoEntity,String>, PagingAndSortingRepository<VideoEntity,String> {

    @Query("update VideoEntity set view_count=:view_count+1 where id=:id")
    List<VideoEntity> findByIdAndViewCount(@Param("id") String id);


}
