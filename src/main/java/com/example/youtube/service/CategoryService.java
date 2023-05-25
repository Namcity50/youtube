package com.example.youtube.service;

import com.example.youtube.dto.category.CategoryDTO;
import com.example.youtube.entity.CategoryEntity;
import com.example.youtube.exps.ItemAlreadyExistException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.CategoryRepository;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO dto) {
        CategoryEntity category = getByName(dto.getName());
        if (category!=null){
            throw new ItemAlreadyExistException("Category already exist");
        }
        CategoryEntity entity = new CategoryEntity();
        entity.setName(dto.getName());
        entity.setPrtId(SpringSecurityUtil.getProfileId());
        entity.setVisible(false);
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;

    }

    private CategoryEntity getByName(String name) {
        return categoryRepository.getByName(name);
    }

    public CategoryDTO update(Integer id, CategoryDTO dto) {
        CategoryEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setVisible(dto.getVisible());
        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public CategoryEntity get(Integer id) {
        Optional<CategoryEntity> optional = categoryRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Item not found: " + id);
        }
        return optional.get();
    }

    public Boolean delete(Integer id) {
        categoryRepository.updateVisible(id);
        return true;
    }


    public Page<CategoryDTO> getAll(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");
        Pageable paging = PageRequest.of(page - 1, size, sort);
        Page<CategoryEntity> pageObj = categoryRepository.findAllVisibleTrue(paging);
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
