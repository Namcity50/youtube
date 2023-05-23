package com.example.youtube.service;

import com.example.youtube.entity.CommentLikeEntity;
import com.example.youtube.enums.LikeStatus;
import com.example.youtube.repository.CommentLikeRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;

    public int like(Integer commentId, Integer profileId) {
        makeEmotion(commentId, profileId, LikeStatus.ROLE_LIKE);
        return commentLikeRepository.likeCount(commentId);
    }

    private void makeEmotion(Integer commentId, Integer profileId, LikeStatus status) {
        Optional<CommentLikeEntity> optional = commentLikeRepository
                .findByCommentIdAndProfileId(commentId, profileId);
        if (optional.isEmpty()) {
            CommentLikeEntity entity = new CommentLikeEntity();
            entity.setCommentId(commentId);
            entity.setProfileId(profileId);
            entity.setStatus(status);
            commentLikeRepository.save(entity);
            // article like count dislike count larni trigger orqali qilingan
        } else {
            commentLikeRepository.update(status, commentId, profileId);
        }
    }


    public int dislike(Integer commentID, Integer profileId) {
        makeEmotion(commentID, profileId, LikeStatus.ROLE_DISLIKE);
        return commentLikeRepository.dislikeCount(commentID);
    }

    public boolean delete(Integer videoId, Integer profileId) {
        commentLikeRepository.delete(videoId, profileId);
        return true;
    }
}
