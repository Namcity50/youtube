package com.example.youtube.repository;

import com.example.youtube.entity.CommentEntity;
import com.example.youtube.entity.VideoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends CrudRepository<CommentEntity, Integer>, PagingAndSortingRepository<CommentEntity, Integer> {
    @Transactional
    @Modifying
    @Query("delete from CommentEntity   where id =:id")
    int deleteArticle(@Param("id") Integer id);

    Page<CommentEntity> findAll(Pageable pageable, Integer id);

    @Query(value = "SELECT c.id,c.content,c.created_date,c.content FROM comment AS c  where  c.id=:id", nativeQuery = true)
    List<CommentEntity> getProfileById(@Param("id") Integer id);
}
