package com.example.youtube.repository;
import com.example.youtube.entity.tag.TagEntity;
import com.example.youtube.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<TagEntity,Integer> {
}
