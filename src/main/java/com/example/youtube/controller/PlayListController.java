package com.example.youtube.controller;

import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.service.PlayListService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/playList")
@AllArgsConstructor
public class PlayListController {

    private final PlayListService playListService;
    @PostMapping("/private/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto){
        return ResponseEntity.ok(playListService.create(dto));
    }

    @PutMapping("/private/update")
    public ResponseEntity<PlaylistDTO> update(@RequestBody PlaylistDTO dto){
        //return ResponseEntity.ok(playListService.update(dto));
        return null;
    }
}
