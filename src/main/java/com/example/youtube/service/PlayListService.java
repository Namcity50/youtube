package com.example.youtube.service;

import com.example.youtube.dto.PlaylistDTO;
import com.example.youtube.entity.PlayListEntity;
import com.example.youtube.repository.PlayListRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlayListService {
    private final PlayListRepository playListRepository;
    public PlaylistDTO create(PlaylistDTO dto) {
        PlayListEntity entity = new PlayListEntity();
        entity.setName(dto.getName());
        entity.setChannelId(dto.getChannelId());
        entity.setDescription(dto.getDescription());
        entity.setStatus(dto.getStatus());
        entity.setOrderNumber(dto.getOrderNumber());
        playListRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }
}
