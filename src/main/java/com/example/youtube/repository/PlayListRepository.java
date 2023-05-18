package com.example.youtube.repository;

import com.example.youtube.entity.PlayListEntity;
import org.springframework.data.repository.CrudRepository;

public interface PlayListRepository extends CrudRepository<PlayListEntity,Integer> {
}
