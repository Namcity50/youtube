package com.example.youtube.repository;

import com.example.youtube.entity.CommentLikeEntity;
import com.example.youtube.enums.LikeStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
    @Modifying
    @Transactional
    @Query("delete from CommentLikeEntity where commentId=?1  and profileId=?2")
    int delete(Integer commentId, Integer profileId);
    @Modifying
    @Transactional
    @Query("update CommentLikeEntity  set status =:status where commentId=:commentId and profileId=:profileId")
    int update(@Param("status") LikeStatus status,
               @Param("commentId") Integer commentId,
               @Param("profileId") Integer profileId);

    Optional<CommentLikeEntity> findByCommentIdAndProfileId(Integer a_id, Integer p_id);

    @Query("select a.like_count from CommentEntity as a where a.id = ?1 ")
    int likeCount(Integer c_id);
    @Query("select a.dislike_count from CommentEntity as a where a.id = ?1 ")
    int dislikeCount(Integer commentID);
}
