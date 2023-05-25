package com.example.youtube.controller;

import com.example.youtube.dto.playListVideo.PlayListVideoDTO;
import com.example.youtube.dto.playListVideo.PlaylistVideoInfoDTO;
import com.example.youtube.service.PlayListVideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/playlistVideo")
@AllArgsConstructor
public class PlayListVideoController {
    private final PlayListVideoService playListVideoService;
    @PostMapping("private/create")
    public ResponseEntity<PlayListVideoDTO> create(@RequestBody PlayListVideoDTO dto){
       return ResponseEntity.ok(playListVideoService.create(dto));
    }
    @PutMapping("/update")
    public ResponseEntity<Boolean> update(@RequestParam("pid") Integer pid ,
                                          @RequestParam("vid") String vid,
                                          @RequestBody PlayListVideoDTO dto){
        log.info("PlayListVideo" + pid,vid);
        return ResponseEntity.ok(playListVideoService.update(pid,vid,dto));
    }
    @PutMapping("/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("pid") Integer pid ,
                                          @RequestParam("vid") String vid){
        log.info("PlayListVideo" + pid,vid);
        return ResponseEntity.ok(playListVideoService.delete(pid,vid));
    }
    @GetMapping("/getByPlayListVideoAll")
    public ResponseEntity<List<PlaylistVideoInfoDTO>> getByList(@RequestParam("id") Integer playListId){
        return ResponseEntity.ok(playListVideoService.getByList(playListId));
    }
}
