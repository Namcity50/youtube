package com.example.youtube.repository.tag;

import com.example.youtube.entity.tag.TagEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Integer> {

}
