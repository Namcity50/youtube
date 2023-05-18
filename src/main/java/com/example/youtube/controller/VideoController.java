package com.example.youtube.controller;

import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.service.VideoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/video")
@AllArgsConstructor
public class VideoController {
    private final VideoService videoService;
    @PostMapping("/private/user/create")
    public ResponseEntity<?> create(@RequestBody VideoDTO dto) {
        return ResponseEntity.ok().body(videoService.create(dto));
    }

    @PutMapping("/private/user/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")String id,
                                    @RequestBody VideoDTO dto) {
        return ResponseEntity.ok().body(videoService.update(id,dto));
    }

    @PutMapping("/private/user/changeVideoStatus/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id")String id,
                                          @RequestBody VideoDTO dto) {
        return ResponseEntity.ok().body(videoService.changeStatus(id,dto));
    }

    @PutMapping("/private/admin/IncreaseViewCount/{id}")
    public ResponseEntity<?> IncreaseViewCount(@PathVariable("id")String id,
                                          @RequestBody VideoDTO dto) {
        return ResponseEntity.ok().body(videoService.IncreaseViewCount(id,dto));
    }


}
