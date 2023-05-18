package com.example.youtube.repository;

import com.example.youtube.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity,Integer> {

}
