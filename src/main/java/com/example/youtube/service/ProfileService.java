package com.example.youtube.service;

import com.example.youtube.dto.attach.AttachDTO;
import com.example.youtube.dto.jwt.JwtDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.profile.ProfileResponseDTO;
import com.example.youtube.entity.AttachEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.repository.AttachRepository;
import com.example.youtube.repository.ProfileRepository;
import com.example.youtube.util.JwtUtil;
import com.example.youtube.util.MD5Util;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final AttachRepository attachRepository;
    private final MailSenderService mailSenderService;
    private final AttachService attachService;

    public Boolean changePassword(String pass) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profileEntity = getByProfileId(profileId);
        int entity = profileRepository.updatePassword(profileEntity.getId(), MD5Util.encode(pass));
        return entity > 0;
    }

    public String updateEmail(String email) {
        Optional<ProfileEntity> pageObj = profileRepository.findByEmail(email);
        if (pageObj.isPresent()) {
            throw new MethodNotAllowedException("This email already registered ");
        }
        mailSenderService.checkLimit(email);
        mailSenderService.sendUpdateEmail(email);
        return "Link send to your email";
    }

    public Boolean updateNameAndSurname(ProfileDTO dto) {
        Integer id = SpringSecurityUtil.getProfileId();
        ProfileEntity entity = getByProfileId(id);
        int i = profileRepository.updateNameAndSurname(entity.getId(), dto.getName(), dto.getSurname());
        return i > 0;
    }

    //    public Boolean changeAttach(MultipartFile file) {
//        Integer id = SpringSecurityUtil.getProfileId();
//        Optional<ProfileEntity> optional = profileRepository.findById(id);
//        ProfileEntity entity = optional.get();
//        try {
//            String pathFolder = attachService.getYmDString(); // 2022/04/23
//            File folder = new File(folderName + "/" + pathFolder);  // attaches/2023/04/26
//            if (!folder.exists()) {
//                folder.mkdirs();
//            }
//            byte[] bytes = file.getBytes();
//            String extension = attachService.getExtension(file.getOriginalFilename());
//        AttachEntity attachEntity = attachService.get(name);
//        if (!entity.getAttachId().equals(attachEntity.getId())) {
//            entity.setAttachId(attachEntity.getId());
//        }
//        profileRepository.deleteById(entity.getId());
//        Path file1 = Paths.get("attaches/" + dto.getPhoto());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        entity.setAttachId(attachEntity.getId());
//        profileRepository.save(entity);
//        return true;
//    }
    public ProfileResponseDTO getDetail() {
        Integer id = SpringSecurityUtil.getProfileId();
        ProfileEntity entity = profileRepository.findByProfile(id);
        ProfileResponseDTO dto = getResponseDto(entity);
        return dto;
    }

    public ProfileDTO createEmployee(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()){
            throw new AppBadRequestException("Item already exist");
        }
        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        if (!(dto.getRole().equals(ProfileRole.ROlE_MODERATOR) || dto.getRole().equals(ProfileRole.ROLE_ADMIN))) {
            throw new AppBadRequestException(" Error profile role: ");
        }
        entity.setRole(dto.getRole());
        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    private ProfileResponseDTO getResponseDto(ProfileEntity entity) {
        ProfileResponseDTO dto = new ProfileResponseDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setPhotoUrl(attachService.getAttachByLink(entity.getPhotoId()));
        return dto;
    }

    public ProfileEntity getByProfileId(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException(" Not found profile");
        });
    }

    public String verificationUpdateEmail(String jwt) {
        JwtDTO jwtDTO = JwtUtil.decodeToUpdateEmail(jwt);
        Integer profileId = jwtDTO.getId();
        String newEmail = jwtDTO.getMail();
        profileRepository.updateEmail(profileId,newEmail);
        return "Successfully updated your email";
    }

    public Object updateImage(MultipartFile file) {
        ProfileEntity profileEntity = getByProfileId(SpringSecurityUtil.getProfileId());
        AttachDTO newPhoto = attachService.save(file);
        AttachEntity oldPhoto = profileEntity.getAttachEntity();
        profileEntity.setPhotoId(newPhoto.getId());
        profileRepository.save(profileEntity);
        //delete old image
        if (oldPhoto != null) {
            attachService.delete(oldPhoto.getId());
        }
        return newPhoto;

    }
}
