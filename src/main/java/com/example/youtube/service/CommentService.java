package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.comment.CommentDTO;
import com.example.youtube.dto.comment.CommentInfoDTO;
import com.example.youtube.dto.comment.CommentResponseDTO;
import com.example.youtube.dto.comment.CommentResponseInfoDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.video.VideoResponseDTO;
import com.example.youtube.entity.CommentEntity;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.mapper.CommentMapperDTO;
import com.example.youtube.mapper.CommentMapperInfoDTO;
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


    //1
    public CommentDTO create(CommentDTO dto) {
        CommentEntity entity = new CommentEntity();
        entity.setProfileId(dto.getProfileId());
        entity.setVideoId(dto.getVideoId());
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
        entity.setCreatedDate(dto.getCreatedDate());
        commentRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    //2
    public CommentDTO update(Integer id, CommentDTO dto) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        CommentEntity vId = get(id);
        if (!profileId.equals(vId)) {
            throw new ItemNotFoundException(" comment not found!!!");
        }
        CommentEntity entity = get(id);
        entity.setProfileId(dto.getProfileId());
        entity.setVideoId(dto.getVideoId());
        entity.setContent(dto.getContent());
        entity.setReplyId(dto.getReplyId());
        dto.setId(entity.getId());
        return dto;
    }

    //3
    public Boolean delete(Integer id) {
        //
        commentRepository.deleteComment(id);
        return true;

    }

    //4
    public Page<CommentDTO> getPag(int page, int size, Integer id) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<CommentEntity> commentEntities = commentRepository.findAll(pageable);

        long totalElements = commentEntities.getTotalElements();
        List<CommentEntity> contentList = commentEntities.getContent();
        List<CommentDTO> list = new LinkedList<>();
        contentList.forEach(entity -> {
            CommentDTO dto = new CommentDTO();
            dto.setId(entity.getId());
            dto.setProfileId(entity.getProfileId());
            dto.setVideoId(entity.getVideoId());
            dto.setContent(entity.getContent());
            list.add(dto);
        });


        return new PageImpl<>(list, pageable, totalElements);
    }

    //5
    public List<CommentResponseDTO> getByProfileIdCommentList(Integer id) {
        List<CommentMapperDTO> entityList = commentRepository.findByProfileId(id);
        List<CommentResponseDTO> list = new LinkedList<>();

        entityList.forEach(entity -> {
            list.add(toCommentShortInfo(entity));
        });
        return list;
    }

    //6
    public List<CommentResponseDTO> getByProfileCommentList(Integer id) {
        List<CommentMapperDTO> entityList = commentRepository.findByProfileId(id);
        List<CommentResponseDTO> list = new LinkedList<>();

        entityList.forEach(entity -> {
            list.add(toCommentShortInfo(entity));
        });
        return list;
    }

    //7
    public List<CommentResponseInfoDTO> getLIstByVideoId(Integer id) {
        List<CommentMapperInfoDTO> mapperDTOList = commentRepository.findByVideoId(id);
        List<CommentResponseInfoDTO> list = new LinkedList<>();
        mapperDTOList.forEach(entity -> {
            list.add(toCommentInfo(entity));
        });
        return list;
    }


    //8
    public List<CommentResponseInfoDTO> getByReplayCommentId(Integer id) {
        List<CommentMapperInfoDTO> mapperDTOList = commentRepository.findByCommentId(id);
        List<CommentResponseInfoDTO> list = new LinkedList<>();
        mapperDTOList.forEach(entity -> {
            list.add(toCommentInfo(entity));
        });
        return list;
    }

    public CommentEntity get(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("comment not found");
        }
        return optional.get();
    }

    private CommentResponseDTO toCommentShortInfo(CommentMapperDTO mapperDTO) {
        CommentResponseDTO dto = new CommentResponseDTO();
        dto.setId(mapperDTO.getId());
        dto.setContent(mapperDTO.getContent());
        dto.setCreatedDate(mapperDTO.getCreatedDate());
        dto.setLikeCount(mapperDTO.getLikeCount());
        dto.setDislikeCount(mapperDTO.getDisLikeCount());
        dto.setVideoResponseDTO(new VideoResponseDTO(mapperDTO.getVideoId(), mapperDTO.getPreviewAttachId(), mapperDTO.getTitle()));
        return dto;
    }

    private CommentResponseInfoDTO toCommentInfo(CommentMapperInfoDTO mapperDTO) {
        CommentResponseInfoDTO dto = new CommentResponseInfoDTO();
        dto.setId(mapperDTO.getId());
        dto.setContent(mapperDTO.getContent());
        dto.setCreatedDate(mapperDTO.getCreatedDate());
        dto.setLikeCount(mapperDTO.getLikeCount());
        dto.setDislikeCount(mapperDTO.getDisLikeCount());
        dto.setProfileDTO(new ProfileDTO(mapperDTO.getProfileId(), mapperDTO.getProfileName(),
                mapperDTO.getProfileSurname(), mapperDTO.getPhoto()));
        return dto;
    }


}
