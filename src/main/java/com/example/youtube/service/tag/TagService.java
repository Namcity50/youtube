package com.example.youtube.service.tag;

import com.example.youtube.dto.tag.TagDTO;
import com.example.youtube.entity.TagEntity;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Page<TagDTO> tagList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<TagEntity> pageObj = tagRepository.findAll(pageable);
        return new PageImpl<>(toList(pageObj.getContent()), pageable, pageObj.getTotalElements());
    }

    private List<TagDTO> toList(List<TagEntity> content) {
        List<TagDTO> dtoList = new ArrayList<>();
        content.forEach(tagEntity -> {
            dtoList.add(toDTO(tagEntity));
        });
        return dtoList;
    }

    public Object update(Integer tagId, TagDTO dto) {
        TagEntity entity = getById(tagId);
        if (!dto.getName().startsWith("#")) {
            entity.setName("#" + dto.getName());
        } else {
            entity.setName(dto.getName());
        }
        entity = tagRepository.save(entity);
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
