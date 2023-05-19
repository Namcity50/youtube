package com.example.youtube.controller;

import com.example.youtube.dto.video.VideoTagDTO;
import com.example.youtube.dto.video.VideoTagResponseDTO;
import com.example.youtube.service.VideoTagService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/videotag")
@AllArgsConstructor
public class VideoTagController {
    private final VideoTagService videoTagService;

    @PostMapping("/private/user/create")
    public ResponseEntity<Boolean> create(@RequestBody VideoTagDTO dto){
        return ResponseEntity.ok(videoTagService.create(dto));
    }
    @DeleteMapping("/private/user/delete")
    private ResponseEntity<Boolean> delete(@RequestParam("tId") Integer tagId,
                                           @RequestParam("vId") String videoId){
        return ResponseEntity.ok(videoTagService.delete(tagId,videoId));
    }
    @GetMapping("private/getByTagList")
    public ResponseEntity<List<VideoTagResponseDTO>> getByVideoTagList(@RequestParam("id") String id){
        return ResponseEntity.ok(videoTagService.getByVideoTagList(id));
    }
}
