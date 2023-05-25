package com.example.youtube.repository;

import com.example.youtube.entity.CategoryEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

    @Transactional
    @Modifying
    @Query("update CategoryEntity  set visible = false where id =:id")
    int updateVisible(@Param("id") Integer id);

    CategoryEntity getByName(String name);

    @Query("from  CategoryEntity as c where c.visible=TRUE ")
    Page<CategoryEntity> findAllVisibleTrue(Pageable paging);
}
