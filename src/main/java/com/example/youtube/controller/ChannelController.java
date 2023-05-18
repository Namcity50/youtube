package com.example.youtube.controller;

import com.example.youtube.dto.channel.ChannelDTO;
import com.example.youtube.service.ChannelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/channel")
@AllArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody ChannelDTO dto) {
        return ResponseEntity.ok(channelService.create(dto));
    }
    @PostMapping("/private/update")
    public ResponseEntity<?> updatePhoto(@RequestBody ChannelDTO dto) {
        return ResponseEntity.ok(channelService.update(dto));
    }

    @PutMapping("/private/update/{photoId}")
    public ResponseEntity<?> updatePhoto(@PathVariable("photoId") String photoId) {
        return ResponseEntity.ok(channelService.updatePhoto(photoId));
    }
    @PutMapping("/private/updateBanner/{bannerId}")
    public ResponseEntity<?> updateBanner(@PathVariable("bannerId") String bannerId) {
        return ResponseEntity.ok(channelService.updateBanner(bannerId));
    }
    @GetMapping("/private/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                        @RequestParam(value = "size",defaultValue = "10") Integer size){
        return ResponseEntity.ok(channelService.pagination(page,size));
    }
    @GetMapping("/public/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id){
        return ResponseEntity.ok(channelService.getChannelById(id));
    }
    @GetMapping("/private/admin/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable("id") String channelId){
        return ResponseEntity.ok(channelService.changeStatus(channelId));
    }
    @GetMapping("/private/getChannel")
    public ResponseEntity<?> getChannel(){
        return ResponseEntity.ok(channelService.getChannel());
    }

}
