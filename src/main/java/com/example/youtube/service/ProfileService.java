package com.example.youtube.service;

import com.example.youtube.dto.AttachDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.profile.ProfileResponseDTO;
import com.example.youtube.entity.AttachEntity;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.repository.AttachRepository;
import com.example.youtube.repository.ProfileRepository;
import com.example.youtube.util.MD5Util;
import com.example.youtube.util.SpringSecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    public Boolean changePassword(String pass) {
        Integer profileId = SpringSecurityUtil.getProfileId();
        ProfileEntity profileEntity = getByProfileId(profileId);
        int entity = profileRepository.updatePassword(profileEntity.getId(), pass);
        return entity > 0;
    }

    public Boolean updateEmail(String email) {
        Integer id = SpringSecurityUtil.getProfileId();
        ProfileEntity profileEntity = getByProfileId(id);
        mailSenderService.checkLimit(email);
        mailSenderService.sendRegistrationEmail(email);
        String s = "Verification link was send to email: " + email;
        Integer i = profileRepository.updateEmail(profileEntity.getId(), email);
        return i > 0;
    }

    public Boolean updateNameAndSurname(ProfileDTO dto ) {
        Integer id = SpringSecurityUtil.getProfileId();
        ProfileEntity entity = getByProfileId(id);
        int i = profileRepository.updateNameAndSurname(entity.getId(), dto.getName(), dto.getSurname());
        return i > 0;
    }

    public Boolean changeAttach(Integer id, ProfileDTO dto) {
//        Optional<ProfileEntity> optional = profileRepository.findById(id);
//        ProfileEntity entity = optional.get();
//        int lastIndex = dto.getPhoto().lastIndexOf(".");
//        String name = dto.getPhoto().substring(0, lastIndex);
//        AttachEntity attachEntity = attachService.get(name);
//        if (!entity.getImage().equals(attachEntity)) {
//            entity.setImage(attachEntity);
//        }
//        profileRepository.deleteById(entity.getId());
//        Path file = Paths.get("attaches/" + dto.getImage());
//        try {                                                     // attaches/2023/4/25/20f0f915-93ec-4099-97e3-c1cb7a95151f.jpg
//            Files.deleteIfExists(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        entity.setImage(attachEntity);
//        profileRepository.save(entity);
        return true;
    }
    public ProfileResponseDTO getDetail(){
        Integer id = SpringSecurityUtil.getProfileId();
        ProfileEntity entity = profileRepository.findByProfile(id);
        ProfileResponseDTO dto = getResponseDto(entity);
        return dto;
    }

    public ProfileDTO createEmployee(ProfileDTO dto) {
        ProfileEntity entity = new ProfileEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
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
        dto.setPhotoUrl(null);
        return dto;
    }

    private ProfileEntity getByProfileId(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException(" Not found profile");
        }
        ProfileEntity entity = optional.get();
        return entity;
    }
}
