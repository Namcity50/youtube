package com.example.youtube.controller;

import com.example.youtube.dto.jwt.JwtDTO;
import com.example.youtube.enums.ProfileRole;
import com.example.youtube.service.ProfileService;
import com.example.youtube.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.headers.HeadersSecurityMarker;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;

    @PutMapping("/private/password")
    public ResponseEntity<Boolean> changePassword(@RequestParam(value = "id") Integer id,
                                                  @RequestParam(value = "pass") String pass,
                                                  HttpServletRequest request ){
        return ResponseEntity.ok(profileService.changePassword(pass));
    }
    @PutMapping("/private")
    public ResponseEntity<String> changeEmail(@PathVariable("id") Integer id, @RequestParam(value = "email") String email){
        return ResponseEntity.ok(profileService.updateEmail(id,email));
    }

}
