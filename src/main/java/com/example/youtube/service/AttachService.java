package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.entity.AttachEntity;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;
    @Value("attaches")
    private String folderName;
    @Value("${server.host}")
    private String domainName;

    public AttachDTO save(MultipartFile file) {
        try {
            String pathFolder = getYmDString(); // 2022/04/23
            File folder = new File(folderName + "/" + pathFolder);  // attaches/2023/04/26
            if (!folder.exists()) {
                folder.mkdirs();
            }
            byte[] bytes = file.getBytes();
            String extension = getExtension(file.getOriginalFilename());

            AttachEntity attachEntity = new AttachEntity();
            attachEntity.setId(UUID.randomUUID().toString());
            attachEntity.setCreatedDate(LocalDateTime.now());
            attachEntity.setExtension(extension);
            attachEntity.setSize(file.getSize());
            attachEntity.setPath(pathFolder);
            attachEntity.setOriginalName(file.getOriginalFilename());
            attachRepository.save(attachEntity);

            Path path = Paths.get(folderName + "/" + pathFolder + "/" + attachEntity.getId() + "." + extension);
            // attaches/2023/04/26/uuid().jpg
            Files.write(path, bytes);
            return toDTO(attachEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] open(String attachId) {
        AttachEntity attachEntity = get(attachId);
        byte[] data;
        try {                 // attaches/2023/4/25/20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
            Path file = Paths.get(folderName + "/" + attachEntity.getPath() + "/" + attachId + "." + attachEntity.getExtension());
            data = Files.readAllBytes(file);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }


    public Resource download(String fileId) {
        try {
            AttachEntity attachEntity = get(fileId);
            Path file = Paths.get(folderName + "/" + attachEntity.getPath() + "/" + fileId + "." + attachEntity.getExtension());
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }


    public String getExtension(String fileName) { // mp3/jpg/npg/mp4.....
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    public String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day; // 2022/04/23
    }

    public AttachEntity get(String id) {
        return attachRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Attach not found");
        });
    }

    public Page<AttachDTO> pagination(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        Page<AttachEntity> entityList = attachRepository.findAll(pageable);
        return new PageImpl<>(toList(entityList.getContent()), pageable, entityList.getTotalElements());
    }

    public boolean delete(String id) {
        AttachEntity entity = get(id);
        File file = new File(folderName + "/" + entity.getPath() + "/" + entity.getId() + "." + entity.getExtension());
        if (file.delete()) {
            attachRepository.delete(entity);
        } else {
            throw new AppBadRequestException("Cannot delete this file");
        }
        return true;
    }

    private List<AttachDTO> toList(List<AttachEntity> entityList) {
        List<AttachDTO> dtoList = new ArrayList<>();
        entityList.forEach(attachEntity -> {
            dtoList.add(toDTO(attachEntity));
        });
        return dtoList;
    }

    public AttachDTO toDTO(AttachEntity entity) {
        AttachDTO dto = new AttachDTO();
        dto.setId(entity.getId());
        dto.setCreatedData(entity.getCreatedDate());
        dto.setOriginalName(entity.getOriginalName());
        dto.setPath(entity.getPath());
        dto.setUrl(domainName + "/api/v1/attach/public/open/" + entity.getId());
        dto.setSize(entity.getSize());
        dto.setExtension(entity.getExtension());
        return dto;
    }

    public String getAttachByLink(String attachId) {
        if (attachId == null) {
            return null;
        }
        return (domainName + "/api/v1/attach/public/open/" + attachId);
    }

}
