package com.example.youtube.controller;

import com.example.youtube.dto.playList.PlayListInfoDTO;
import com.example.youtube.dto.playList.PlayListResponseDTO;
import com.example.youtube.dto.playList.PlayListShortInfoDTO;
import com.example.youtube.dto.playList.PlaylistDTO;
import com.example.youtube.service.PlayListService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/playlist")
@AllArgsConstructor
public class PlayListController {
    private final PlayListService playListService;
    @PostMapping("/private/user/create")
    public ResponseEntity<PlaylistDTO> create(@RequestBody PlaylistDTO dto){
        return ResponseEntity.ok(playListService.create(dto));
    }
    @PutMapping("/private/user/update")
    public ResponseEntity<Boolean> updateUserAndOwner(@RequestParam("id") Integer id ,
                                                      @RequestBody PlaylistDTO dto){
        return ResponseEntity.ok(playListService.update(id,dto));
    }

    @PutMapping("/private/user/changeStatus")
    public ResponseEntity<Boolean> updateStatus(@RequestParam("id") Integer id){
        return ResponseEntity.ok(playListService.updateStatus(id));
    }
    @DeleteMapping("/private/delete")
    public ResponseEntity<Boolean> delete(@RequestParam("id") Integer id ){
        return ResponseEntity.ok(playListService.delete(id));
    }
    @GetMapping("/private/admin/pagination")
    public ResponseEntity<Page<PlayListInfoDTO>> getPagination(@RequestParam(value = "page",defaultValue = "0") Integer page,
                                                              @RequestParam(value = "size",defaultValue = "6") Integer size){
          return ResponseEntity.ok(playListService.getPagination(page,size));
    }
    @GetMapping("/private/admin/getList")
    public ResponseEntity<List<PlayListInfoDTO>> getListByProfileId(@RequestParam("id") Integer id){
        return ResponseEntity.ok(playListService.getListByProfileId(id));
    }
    @GetMapping("private/user/getUserPlayList")
    public ResponseEntity<List<PlayListShortInfoDTO>> getUserList(){
        return ResponseEntity.ok(playListService.getUserList());
    }
    @GetMapping("private/user/getChannelPlayList")
    public ResponseEntity<PlayListShortInfoDTO> getChannelPlayList(String id){
        return ResponseEntity.ok(playListService.getChannelPlayList(id));
    }
    @GetMapping("private/user/getByPlayListId")
    public ResponseEntity<PlayListResponseDTO> getByPlayListId(Integer id){
        return ResponseEntity.ok(playListService.getByPlayListId(id));
    }
}
