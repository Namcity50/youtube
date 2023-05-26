package com.example.youtube.controller;

import com.example.youtube.dto.profile.ProfileDTO;
import com.example.youtube.dto.profile.ProfileResponseDTO;
import com.example.youtube.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
@Tag(name = "Profile Api list", description = "Api list for profiles")
@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {
    private final ProfileService profileService;
    @Operation(summary = "Retrieve a Tutorial by Id",
            description = "Get a Tutorial object by specifying its id. ")
    @PutMapping("/private/password")
    public ResponseEntity<Boolean> changePassword(@RequestParam(value = "pass") String pass) {
        return ResponseEntity.ok(profileService.changePassword(pass));
    }

    @PutMapping("/private/updateEmail")
    public ResponseEntity<String > changeEmail(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(profileService.updateEmail(email));
    }

    @PostMapping("/private/updateNameAndSurname")
    public ResponseEntity<Boolean> changeNameAndSurname(@RequestBody ProfileDTO dto) {
        log.info("create profile {}", dto);
        return ResponseEntity.ok(profileService.updateNameAndSurname(dto));
    }

    @GetMapping("")
    public ResponseEntity<ProfileResponseDTO> getDetail() {
        return ResponseEntity.ok(profileService.getDetail());
    }

    @PostMapping("/private/admin/create")
    public ResponseEntity<ProfileDTO> createEmployee(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.createEmployee(dto));
    }

    @GetMapping("/public/email/update/{jwt}")
    public ResponseEntity<?> verificationUpdateEmail(@PathVariable("jwt") String jwt){
        return ResponseEntity.ok(profileService.verificationUpdateEmail(jwt));
    }
    @PostMapping("/private/update/photo")
    public ResponseEntity<?> updateImage(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(profileService.updateImage(file));
    }


}
