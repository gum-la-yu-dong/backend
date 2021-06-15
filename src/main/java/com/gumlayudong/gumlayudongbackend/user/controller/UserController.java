package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import com.gumlayudong.gumlayudongbackend.user.dto.UserSaveRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserSaveRequest userSaveRequest) {
        try {
            String result = userService.save(userSaveRequest);
            System.out.println("result = " + result);
            return ResponseEntity.created(URI.create("/api/users")).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
