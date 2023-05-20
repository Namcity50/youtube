package com.example.youtube.service;

import com.example.youtube.dto.comment.CommentDTO;
import com.example.youtube.dto.comment.CommentResponseDTO;
import com.example.youtube.dto.video.VideoResponseDTO;
import com.example.youtube.entity.CommentEntity;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.mapper.CommentMapperDTO;
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
        dto.setProfileId(entity.getProfileId());
        dto.setVideoId(entity.getVideoId());
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
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
        dto.setProfileId(entity.getProfileId());
        dto.setVideoId(entity.getVideoId());;
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
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
            commentRepository.deleteComment(id);
            return true;

    }

    public Page<CommentDTO> getPag(int page, int size, Integer id) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentEntities = commentRepository.findAll(pageable);

        long totalElements = commentEntities.getTotalElements();
        List<CommentEntity> contentList = commentEntities.getContent();
        List<CommentDTO> list = new LinkedList<>();
        contentList.forEach(entity ->{
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setProfileId(entity.getProfileId());
            dto.setVideoId(entity.getVideoId());
            dto.setContent(entity.getContent());
            dto.setLike_count(entity.getLike_count());
            dto.setDislike_count(entity.getDislike_count());
            list.add(dto);
        });



        return new PageImpl<>(list,pageable,totalElements);
    }



        public List<CommentResponseDTO> getByProfileIdCommentList(Integer id) {
            List<CommentMapperDTO> entityList = commentRepository.findByProfileId(id);
            List<CommentResponseDTO> list = new LinkedList<>();

            entityList.forEach(entity->{
                list.add(toCommentShortInfo(entity));
            });
            return list;
        }

    private CommentResponseDTO toCommentShortInfo(CommentMapperDTO mapperDTO) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(mapperDTO.getId());
        dto.setContent(mapperDTO.getContent());
        dto.setCreated_date(mapperDTO.getCreatedDate());
        dto.setLike_count(mapperDTO.getLikeCount());
        dto.setDislike_count(mapperDTO.getDisLikeCount());
        dto.setVideoResponseDTO(new VideoResponseDTO(mapperDTO.getId(),mapperDTO.getPreviewAttachId(),mapperDTO.getTitle()));
        return dto;
    }

    public List<CommentResponseDTO>  getByProfileCommentList(Integer id) {
            List<CommentMapperDTO> entityList = commentRepository.findByProfileId(id);
            List<CommentResponseDTO> list = new LinkedList<>();

            entityList.forEach(entity->{
                list.add(toCommentShortInfo(entity));
            });
            return list;
    }
}
