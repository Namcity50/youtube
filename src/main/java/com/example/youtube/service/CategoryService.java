package com.example.youtube.service;

import com.example.youtube.dto.CategoryDTO;
import com.example.youtube.entity.CategoryEntity;
import com.example.youtube.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Integer create(CategoryDTO dto, Integer jwtId) {
        CategoryEntity entity = new CategoryEntity();

        entity.setName(dto.getName());
        entity.setPrtId(jwtId);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return entity.getId();

    }
}
