package com.example.youtube.service.tag;

import com.example.youtube.dto.tag.TagCreateDTO;
import com.example.youtube.dto.tag.TagResponseDTO;
import com.example.youtube.dto.tag.TagUpdateDTO;
import com.example.youtube.entity.tag.TagEntity;
import com.example.youtube.enums.Language;
import com.example.youtube.exps.TagNotFound;
import com.example.youtube.repository.tag.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@Service
@AllArgsConstructor
public class TagService {
    private final TagRepository tagRepository;



    public TagResponseDTO create(TagCreateDTO dto) {
        TagEntity tagEntity = new TagEntity();
        if (!dto.getName().startsWith("#")) {
            tagEntity.setName("#" + dto.getName());
        } else {
            tagEntity.setName(dto.getName());
        }

        tagEntity.setCreatedDate(LocalDateTime.now());
        tagRepository.save(tagEntity);

        TagResponseDTO response = new TagResponseDTO();
        response.setId(tagEntity.getId());
        response.setName(tagEntity.getName());
        response.setCreatedDate(LocalDateTime.now());

        return response;
    }

    public Boolean updateById(Integer id, TagUpdateDTO tagUpdateDTO, Language language) {
        Optional<TagEntity> byId = tagRepository.findById(id);

        if (byId.isEmpty()) {
            throw new TagNotFound("Tag not found");
        }

        TagEntity tagEntity = byId.get();
        if (!tagUpdateDTO.getName().startsWith("#")) {
            tagEntity.setName("#" + tagUpdateDTO.getName());
        } else {
            tagEntity.setName(tagUpdateDTO.getName());
        }

        tagRepository.save(tagEntity);
        return true;
    }

    public Boolean deleteById(Integer id, Language language) {
        Optional<TagEntity> byId = tagRepository.findById(id);

        if (byId.isEmpty()) {
            throw new TagNotFound("Tag not found");
        }

        tagRepository.delete(byId.get());
        return true;
    }

    public List<TagResponseDTO> tagList() {
        Iterable<TagEntity> all = tagRepository.findAll();

        List<TagResponseDTO> result = new ArrayList<>();
        for (TagEntity tagEntity : all) {
            TagResponseDTO response = new TagResponseDTO();
            response.setId(tagEntity.getId());
            response.setName(tagEntity.getName());
            response.setCreatedDate(tagEntity.getCreatedDate());
            result.add(response);
        }

        return result;
    }
}
