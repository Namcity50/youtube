package com.example.youtube.controller;

import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/channel")
@AllArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody ChannelDTO dto) {
        return ResponseEntity.ok(channelService.create(dto));
    }
}
