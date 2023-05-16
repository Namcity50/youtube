package com.example.youtube.controller;

import com.example.youtube.dto.CategoryDTO;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.service.CategoryService;
import com.example.youtube.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping({"", "/"})
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto,
                                          HttpServletRequest request) {
        JwtUtil.checkForRequiredRole(request, ProfileRole.ROLE_ADMIN);
        Integer jwtId = (Integer) request.getAttribute("id");
        return ResponseEntity.ok(categoryService.create(dto, jwtId));
    }

}
