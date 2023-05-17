package com.example.youtube.controller;

import com.example.youtube.dto.PlaylistDTO;
import com.example.youtube.service.PlayListService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/playList")
@AllArgsConstructor
public class PlayListController {
    private final PlayListService playListService;
    @PostMapping("/private/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto){
        return ResponseEntity.ok(playListService.create(dto));
    }
}
