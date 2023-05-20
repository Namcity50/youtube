package com.example.youtube.controller;

import com.example.youtube.dto.comment.CommentDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.repository.CommentRepository;
import com.example.youtube.service.CommentService;
import lombok.AllArgsConstructor;
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
    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto) {
        return ResponseEntity.ok().body(commentService.create(dto));
    }

    @PutMapping("/private/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")Integer id,
                                    @RequestBody CommentDTO dto) {
        return ResponseEntity.ok().body(commentService.update(id,dto));
    }

    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Integer id) {
        return ResponseEntity.ok().body(commentService.delete(id));
    }


    @GetMapping("/public/getPaging")
    public ResponseEntity<?> Paging(@RequestParam(value = "page",defaultValue = "1")int page,
                                              @RequestParam(value = "size",defaultValue = "10")int size,
                                              @RequestParam(value = "id")Integer id){
        return ResponseEntity.ok(commentService.getPag(page,size,id));
    }

    @GetMapping("/public/getByProfileId/{id}")
    public ResponseEntity<?> getProfileId(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getByProfileIdCommentList(id));
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/public/getByProfile")
    public ResponseEntity<?> getProfile(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getByProfileCommentList(id));
    }   //



    @GetMapping("/public/getByVideoId")
    public ResponseEntity<?> getByVideoId(@PathVariable( "id")Integer id){
        return ResponseEntity.ok(commentService.getLIstByVideoId(id));
    }


}
