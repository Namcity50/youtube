package com.example.youtube.controller;

import com.example.youtube.dto.category.CategoryDTO;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.service.CategoryService;
import com.example.youtube.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/private/admin/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.create(dto));
    }


    @PutMapping("/private/admin/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id,
                                          @RequestBody CategoryDTO dto) {
        return ResponseEntity.ok(categoryService.update(id, dto));
    }

    @DeleteMapping("/private/admin/delete/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(categoryService.delete(id));
    }

    @GetMapping("/getAll")
    public ResponseEntity<Page<CategoryDTO>> getAll(@RequestParam(value = "page", defaultValue = "1") int page,
                                                    @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(categoryService.getAll(page, size));
    }



}
