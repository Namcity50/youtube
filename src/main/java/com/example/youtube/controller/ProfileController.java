package com.example.youtube.controller;

import com.example.youtube.dto.jwt.JwtDTO;
import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.profile.ProfileResponseDTO;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.service.ProfileService;
import com.example.youtube.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.headers.HeadersSecurityMarker;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @PutMapping("/private/password")
    public ResponseEntity<Boolean> changePassword(@RequestParam(value = "pass") Integer pass){

        return ResponseEntity.ok(profileService.changePassword(pass));
    }

    @PutMapping("/private/updateEmail")
    public ResponseEntity<Boolean> changeEmail(@RequestParam(value = "email") String email ){
        return ResponseEntity.ok(profileService.updateEmail(email));
    }

    @PostMapping("/private/updateNameAndSurname")
    public ResponseEntity<Boolean> changeNameAndSurname(@RequestBody ProfileDTO dto){
        LOGGER.info("create profile {}",dto);
        return ResponseEntity.ok(profileService.updateNameAndSurname(dto));
    }

    @GetMapping("")
    public ResponseEntity<ProfileResponseDTO> getDetail(){
        return ResponseEntity.ok(profileService.getDetail());
    }
    @PostMapping("/private/admin/create")
    public ResponseEntity<ProfileDTO> createEmployee(@RequestBody ProfileDTO dto){
        return ResponseEntity.ok(profileService.createEmployee(dto));
    }

}
