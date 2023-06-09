package com.example.youtube.service;

import com.example.youtube.dto.auth.AuthDTO;
import com.example.youtube.dto.auth.AuthResponseDTO;
import com.example.youtube.dto.auth.RegistrationDTO;
import com.example.youtube.entity.ProfileEntity;
import com.example.youtube.enums.GeneralStatus;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.exps.AppBadRequestException;
import com.example.youtube.exps.ItemNotFoundException;
import com.example.youtube.exps.MethodNotAllowedException;
import com.example.youtube.repository.ProfileRepository;
import com.example.youtube.util.JwtUtil;
import com.example.youtube.util.MD5Util;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AuthService {

    private final ProfileRepository profileRepository;
    private final   MailSenderService mailSenderService;
    public Object registration(RegistrationDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent() && optional.get().getStatus() != GeneralStatus.ROLE_REGISTER) {
            throw new ItemNotFoundException("Email already exists.");
        }
        // check email limit
        mailSenderService.checkLimit(dto.getEmail());

        ProfileEntity entity = null;
        if (optional.isEmpty()) {
            entity = new ProfileEntity();
        } else {
            entity = optional.get();
        }
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setRole(ProfileRole.ROLE_USER);
        entity.setEmail(dto.getEmail());
        entity.setPassword(MD5Util.getMd5Hash(dto.getPassword()));
        entity.setStatus(GeneralStatus.ROLE_REGISTER);
        // send email
        mailSenderService.sendRegistrationEmail(dto.getEmail());
//        mailSenderService.sendRegistrationEmailMime(dto.getEmail());
        // save
        profileRepository.save(entity);
        String s = "Verification link was send to email: " + dto.getEmail();
        return new String(s);
    }
    public AuthResponseDTO login(AuthDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmailAndPasswordAndVisible(
                dto.getEmail(),
                MD5Util.getMd5Hash(dto.getPassword()),
                true);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email or password incorrect");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ROLE_ACTIVE))
            throw new MethodNotAllowedException("Wrong status,Method not allowed");
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        responseDTO.setName(entity.getName());
        responseDTO.setSurname(entity.getSurname());
        responseDTO.setRole(entity.getRole());
        responseDTO.setJwt(JwtUtil.encode(entity.getEmail(), entity.getRole()));
        return responseDTO;
    }
    public Object emailVerification(String jwt) {
        String email = JwtUtil.decodeEmailVerification(jwt);
        Optional<ProfileEntity> optional = profileRepository.findByEmail(email);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("Email not found.");
        }
        ProfileEntity entity = optional.get();
        if (!entity.getStatus().equals(GeneralStatus.ROLE_REGISTER)) {
            throw new AppBadRequestException("Wrong status");
        }
        entity.setStatus(GeneralStatus.ROLE_ACTIVE);
        profileRepository.save(entity);
        return new String("Registration Done");
    }
}
