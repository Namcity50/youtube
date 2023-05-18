package com.example.youtube.service;

import com.example.youtube.dto.comment.CommentDTO;
import com.example.youtube.dto.video.VideShortInfoDTO;
import com.example.youtube.dto.video.VideoDTO;
import com.example.youtube.entity.CommentEntity;
import com.example.youtube.entity.VideoEntity;
import com.example.youtube.enums.VideoStatus;
import com.example.youtube.enums.VideoType;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.CommentRepository;
import com.example.youtube.util.SpringSecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setProfile_id(dto.getProfile_id());
        entity.setVideo_id(dto.getVideo_id());
        entity.setContent(dto.getContent());
        entity.setReply_id(dto.getReply_id());
        entity.setLike_count(dto.getLike_count());
        entity.setDislike_count(dto.getDislike_count());
        commentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public CommentDTO update(Integer id, CommentDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        CommentEntity vId = get(id);
        if (!profileId.equals(vId)) {
            throw new ItemNotFoundException(" comment not found!!!");
        }
        CommentEntity entity = get(id);
        entity.setProfile_id(dto.getProfile_id());
        entity.setVideo_id(dto.getVideo_id());
        entity.setContent(dto.getContent());
        entity.setReply_id(dto.getReply_id());
        entity.setLike_count(dto.getLike_count());
        entity.setDislike_count(dto.getDislike_count());
        dto.setId(entity.getId());
        return dto;
    }


    public CommentEntity get(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("comment not found");
        }
        return optional.get();
    }

    public Boolean delete(Integer id) {
            commentRepository.deleteArticle(id);
            return true;

    }

    public Page<CommentDTO> getPag(int page, int size, Integer id) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentEntities = commentRepository.findAll(pageable, id);

        long totalElements = commentEntities.getTotalElements();
        List<CommentEntity> contentList = commentEntities.getContent();
        List<CommentDTO> list = new LinkedList<>();
        contentList.forEach(entity ->{
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setProfile_id(entity.getProfile_id());
            dto.setVideo_id(entity.getVideo_id());
            dto.setContent(entity.getContent());
            dto.setLike_count(entity.getLike_count());
            dto.setDislike_count(entity.getDislike_count());
            list.add(dto);
        });



        return new PageImpl<>(list,pageable,totalElements);
    }


    public List<CommentDTO> getProfileById(Integer id) {
        List<CommentEntity> listOfArticle = commentRepository.getProfileById(id);
        List<CommentDTO> dtoList = new LinkedList<>();
        listOfArticle.forEach(entity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setProfile_id(entity.getProfile_id());
            dto.setVideo_id(entity.getVideo_id());
            dto.setContent(entity.getContent());
            dto.setLike_count(entity.getLike_count());
            dto.setDislike_count(entity.getDislike_count());
            dto.setCreate_date(entity.getCreated_date());
            dtoList.add(dto);
        });
        return dtoList;
    }


}
