package com.example.youtube.repository;

import com.example.youtube.entity.CommentEntity;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.mapper.CommentMapperDTO;
import com.example.youtube.mapper.CommentMapperInfoDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
    @Transactional
    @Modifying
    @Query("delete from CommentEntity   where id =:id")
    int deleteComment(@Param("id") Integer id);

    Page<CommentEntity> findAll(Pageable pageable);

    @Query(value = "select  c.id as id from  comment as c inner  join profile as p " +
            "on p.id=c.profile_id where c.profile_id=:id", nativeQuery = true)
    List<CommentMapperDTO> findByProfileId(Integer id);

    @Query(value = "select c.id as id from  comment as c inner  join  video as v " +
            " on v.id=c.video_id where c.vido_id =:id ", nativeQuery = true)
    List<CommentMapperInfoDTO> findByVideoId(Integer id);
}
