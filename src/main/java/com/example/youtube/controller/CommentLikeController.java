package com.example.youtube.controller;

import com.example.youtube.service.CommentLikeService;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment_like")
@AllArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PutMapping("/private/like/{c_id}")
    public ResponseEntity<?> like(@PathVariable("c_id") Integer commentID) {
        return ResponseEntity.ok(commentLikeService.like(commentID, SpringSecurityUtil.getProfileId()));
    }

    @PutMapping("/private/dislike/{a_id}")
    public ResponseEntity<?> dislike(@PathVariable("a_id") Integer commentID) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        return ResponseEntity.ok(commentLikeService.dislike(commentID, profileId));
    }

    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer commentID) {
        return ResponseEntity.ok(commentLikeService.delete(commentID, SpringSecurityUtil.getProfileId()));
    }

}
