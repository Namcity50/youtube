package com.example.youtube.controller.tag;

import com.example.youtube.dto.tag.TagCreateDTO;
import com.example.youtube.dto.tag.TagResponseDTO;
import com.example.youtube.dto.tag.TagUpdateDTO;
import com.example.youtube.enums.Language;
import com.example.youtube.service.tag.TagService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/create")
    public ResponseEntity<TagResponseDTO> create(@Valid @RequestBody TagCreateDTO dto) {
        TagResponseDTO result = tagService.create(dto);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Boolean> update(@PathVariable Integer id, @RequestBody TagUpdateDTO dto,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        Boolean result = tagService.updateById(id, dto, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Integer id,
                                          @RequestHeader(value = "Accept-Language", defaultValue = "RU") Language language) {
        Boolean result = tagService.deleteById(id, language);
        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/list")
    public ResponseEntity<List<TagResponseDTO>> getList() {
        List<TagResponseDTO> result = tagService.tagList();
        return ResponseEntity.ok(result);
    }
}