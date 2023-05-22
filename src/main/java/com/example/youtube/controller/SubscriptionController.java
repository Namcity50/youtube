package com.example.youtube.controller;

import com.example.youtube.dto.SubscriptionCreatDTO;
import com.example.youtube.dto.SubscriptionDTO;
import com.example.youtube.dto.SubscriptionUpdateDTO;
import com.example.youtube.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/subscription")
@AllArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    @PostMapping("/private/create")
    public ResponseEntity<?> create(@RequestBody @Valid SubscriptionCreatDTO dto){
        return ResponseEntity.ok(subscriptionService.create(dto));
    }
    @PostMapping("/private/update")
    public ResponseEntity<?> changeStatus(@RequestBody @Valid SubscriptionUpdateDTO dto){
        return ResponseEntity.ok(subscriptionService.changeStatus(dto));
    }
    @PostMapping("/private/notification")
    public ResponseEntity<?> changeNotification(@RequestBody @Valid SubscriptionUpdateDTO dto){
        return ResponseEntity.ok(subscriptionService.changeNotification(dto));
    }
}
