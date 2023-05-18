package com.example.youtube.controller;

import com.example.youtube.dto.tag.TagDTO;
import com.example.youtube.service.tag.TagService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("/private/")
    public ResponseEntity<?> create(@RequestBody @Valid TagDTO dto) {
        return ResponseEntity.ok(tagService.create(dto));
    }

    @PostMapping("/private/admin/update/{id}")
    public ResponseEntity<?> update(@RequestBody TagDTO dto,
                                    @PathVariable("id") Integer tagId) {
        return ResponseEntity.ok(tagService.update(tagId, dto));
    }

    @DeleteMapping("/private/admin/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Integer id) {
        tagService.delete(id);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/public/list")
    public ResponseEntity<?> getList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(tagService.tagList(page, size));
    }
//
//    @PostMapping("/private/tag")
//    public ResponseEntity<?> addTagToArticle(@RequestParam("a_id") String articleId,
//                                             @RequestParam("t_id") Integer tagId,
//                                             HttpServletRequest request) {
//        JwtUtil.checkForRequiredRoleAndGetPrtId(request, ProfileRole.MODERATOR);
//        tagService.addTagToArticle(articleId, tagId);
//        return ResponseEntity.ok().build();
//    }
}
