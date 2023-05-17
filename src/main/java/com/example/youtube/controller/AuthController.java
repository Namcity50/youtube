package com.example.youtube.controller;

import com.example.youtube.dto.auth.AuthDTO;
import com.example.youtube.dto.auth.RegistrationDTO;
import com.example.youtube.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/public/register")
    public ResponseEntity<?> registration(@RequestBody @Valid RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @GetMapping("/public/email/verification/{jwt}")
    public ResponseEntity<?> emailVerification(@PathVariable("jwt") String jwt) {
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
    @PostMapping("/public")
    public ResponseEntity<?> login(@RequestBody @Valid AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
}
