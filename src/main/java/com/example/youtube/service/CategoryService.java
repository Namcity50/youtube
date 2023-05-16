package com.example.youtube.service;

import com.example.youtube.dto.category.CategoryDTO;
import com.example.youtube.entity.CategoryEntity;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

    public Boolean update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);
        entity.setName(dto.getName());
        categoryRepository.save(entity);
        return true;
    }

    public CategoryEntity get(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + id);
        }
        return optional.get();
    }

    public Boolean delete(Integer id, Integer jwtId) {
       categoryRepository.updateVisible(id,jwtId);
       return true;
    }


    public Page<CategoryDTO> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAll(paging);
        long totalCount = pageObj.getTotalElements();

        List<CategoryEntity> entityList = pageObj.getContent();
        List<CategoryDTO> dtoList = new LinkedList<>();


        for (CategoryEntity entity : entityList) {
            CategoryDTO dto = new CategoryDTO();
            dto.setId(entity.getId());
            dto.setCreated_date(entity.getCreated_date());
            dto.setVisible(entity.getVisible());
            dtoList.add(dto);
        }
        return new PageImpl<CategoryDTO>(dtoList, paging, totalCount);
    }


}
