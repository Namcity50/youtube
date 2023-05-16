package com.example.youtube.repository;

import com.example.youtube.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity,Integer>,
        PagingAndSortingRepository<CategoryEntity,Integer> {
}
