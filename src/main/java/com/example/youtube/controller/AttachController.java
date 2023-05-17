package com.example.youtube.controller;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.service.AttachService;
import com.example.youtube.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/attach")
public class AttachController {
    @Autowired
    private AttachService attachService;

    @PostMapping("/public/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO attachDTO = attachService.save(file);
        return ResponseEntity.ok().body(attachDTO);
    }

    @GetMapping(value = "/public/open/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        return attachService.open(fileName);
    }

    @GetMapping("/public/download/{fileId}")
    public ResponseEntity<Resource> download(@PathVariable("fileId") String fileId) {
        Resource file = attachService.download(fileId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/private/admin/pagination")
    public ResponseEntity<?> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(attachService.pagination(page, size));
    }

    @DeleteMapping("/private/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id) {
        return ResponseEntity.ok(attachService.delete(id));
    }
}
