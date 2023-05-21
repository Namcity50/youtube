package com.example.youtube.controller;

import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.dto.video.VideoUpdateDTO;
import com.example.youtube.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/video")
@AllArgsConstructor
public class VideoController {
    private final VideoService videoService;

    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody VideoDTO dto) {
        return ResponseEntity.ok().body(videoService.create(dto));
    }

    @PutMapping("/private/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id,
                                    @RequestBody VideoUpdateDTO dto) {
        return ResponseEntity.ok().body(videoService.update(id, dto));
    }

    @PutMapping("/private/status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(videoService.changeStatus(id));
    }

    @PutMapping("/private/view_c/{id}")
    public ResponseEntity<?> increaseViewCount(@PathVariable("id") String id) {
        return ResponseEntity.ok().body(videoService.increaseViewCount(id));
    }

    @GetMapping("/public/paging/{c_id}")
    public ResponseEntity<?> pagingByCategory(@RequestParam(value = "page", defaultValue = "1") int page,
                                              @RequestParam(value = "size", defaultValue = "10") int size,
                                              @PathVariable(value = "c_id") Integer categoryId) {
        return ResponseEntity.ok(videoService.pagingByCategory(page, size, categoryId));
    }

    @GetMapping("/public/title")
    public ResponseEntity<?> pagingByTitle(@RequestParam(value = "page", defaultValue = "1") int page,
                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                           @RequestParam(value = "text") String text) {
        return ResponseEntity.ok(videoService.pagingByTitle(page, size, text));
    }

    @GetMapping("/public/tag/{id}")
    public ResponseEntity<?> pagingByTag(@RequestParam(value = "page", defaultValue = "1") int page,
                                         @RequestParam(value = "size", defaultValue = "10") int size,
                                         @PathVariable(value = "id") Integer tagId) {
        return ResponseEntity.ok(videoService.pagingByTag(page, size, tagId));
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<?> getVideoById(@PathVariable(value = "id") String videoId) {
        return ResponseEntity.ok(videoService.getVideoById(videoId));
    }

    @GetMapping("/private/admin")
    public ResponseEntity<?> getVideoListAdmin(@RequestParam(value = "page", defaultValue = "1") int page,
                                               @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(videoService.getVideoListAdmin(page, size));
    }

    @GetMapping("/public/videos/{channelId}")
    public ResponseEntity<?> getChannelVideoList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                 @RequestParam(value = "size", defaultValue = "10") int size,
                                                 @PathVariable("channelId") String channelId) {
        return ResponseEntity.ok(videoService.getChannelVideoList(page, size,channelId));
    }

}
