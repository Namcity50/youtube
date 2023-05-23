package com.example.youtube.controller;

import com.example.youtube.dto.comment.CommentDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.repository.CommentRepository;
import com.example.youtube.service.CommentService;
import com.example.youtube.service.ProfileService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/v1/comment")
@AllArgsConstructor
 @EnableWebSecurity
public class CommentController {
    private final CommentService commentService;
    private static final Logger LOGGER =  LoggerFactory.getLogger(ProfileService.class);

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto) {
        LOGGER.trace("trace");
        LOGGER.debug("debug");
        LOGGER.warn("warn");
        LOGGER.error("error");
        return ResponseEntity.ok().body(commentService.create(dto));
    }

//    @PreAuthorize("hasRole('USER')")
    @PostMapping("/private/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Integer id,
                                    @RequestBody CommentDTO dto) {
        return ResponseEntity.ok().body(commentService.update(id,dto));
    }


    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id) {
        return ResponseEntity.ok().body(commentService.delete(id));
    }


//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/public/getPaging")
    public ResponseEntity<?> Paging(@RequestParam(value = "page",defaultValue = "1")int page,
                                              @RequestParam(value = "size",defaultValue = "10")int size){
        return ResponseEntity.ok(commentService.getPag(page,size));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/public/getByProfileId/{id}")
    public ResponseEntity<?> getProfileId(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getByProfileIdCommentList(id));
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/public/getByProfile")
    public ResponseEntity<?> getProfile(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getByProfileCommentList(id));
    }   //



    @GetMapping("/public/getByVideoId/{id}")
    public ResponseEntity<?> getByVideoId(@PathVariable( "id")String id){
        return ResponseEntity.ok(commentService.getLIstByVideoId(id));
    }

    @GetMapping("/public/getByReplayId/{id}")
    public ResponseEntity<?> getByReplayCommentId(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getByReplayCommentId(id));
    }


}
