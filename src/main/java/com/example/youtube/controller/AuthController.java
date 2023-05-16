package com.example.youtube.controller;

import com.example.youtube.dto.AuthDTO;
import com.example.youtube.dto.RegistrationDTO;
import com.example.youtube.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
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
