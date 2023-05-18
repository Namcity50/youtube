package com.example.youtube.repository;

import com.example.youtube.entity.PlayListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface PlayListRepository extends CrudRepository<PlayListEntity,Integer> {
    @Modifying
    @Transactional
    @Query(" update PlayListEntity set status = ?2 where id = ?1 ")
    int updateStatus(Integer id, String status);
}
