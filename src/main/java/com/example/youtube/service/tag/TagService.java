package com.example.youtube.service.tag;

import com.example.youtube.dto.tag.TagDTO;
import com.example.youtube.dto.tag.TagResponseDTO;
import com.example.youtube.dto.tag.TagUpdateDTO;
import com.example.youtube.enums.Language;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.youtube.entity.tag.TagEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;

    public TagDTO create(TagDTO dto) {
        TagEntity tagEntity = new TagEntity();
        if (!dto.getName().startsWith("#")) {
            tagEntity.setName("#" + dto.getName());
        } else {
            tagEntity.setName(dto.getName());
        }
        tagEntity.setCreatedDate(LocalDateTime.now());
        tagRepository.save(tagEntity);
        //return
        dto.setCreatedDate(tagEntity.getCreatedDate());
        dto.setId(tagEntity.getId());
        dto.setName(tagEntity.getName());
        return dto;
    }
     public List<TagResponseDTO> tagList(Integer page, Integer size) {
         Pageable pageable = PageRequest.of(page-1,size);
        Iterable<TagEntity> all = tagRepository.findAll(pageable);

        List<TagResponseDTO> result = new ArrayList<>();
        for (com.example.youtube.entity.tag.TagEntity tagEntity : all) {
            TagResponseDTO response = new TagResponseDTO();
            response.setId(tagEntity.getId());
            response.setName(tagEntity.getName());
            response.setCreatedDate(tagEntity.getCreatedDate());
            result.add(response);
        }

        return result;
    }

    public Object update(Integer tagId, TagDTO dto) {
        TagEntity entity = getById(tagId);
        if (!dto.getName().startsWith("#")) {
            dto.setName("#" + dto.getName());
        } else {
            dto.setName(dto.getName());
        }
        tagRepository.save(entity);
        return toDTO(entity);
    }

    private TagDTO toDTO(TagEntity entity) {
        TagDTO dto = new TagDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    private TagEntity getById(Integer tagId) {
        return tagRepository.findById(tagId).orElseThrow(() -> {
            throw new ItemNotFoundException("Tag not found");
        });
    }

    public void delete(Integer id) {
        tagRepository.delete(getById(id));
    }
}
