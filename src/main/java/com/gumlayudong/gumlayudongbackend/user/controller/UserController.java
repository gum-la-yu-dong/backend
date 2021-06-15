package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import com.gumlayudong.gumlayudongbackend.user.dto.UserResponse;
import com.gumlayudong.gumlayudongbackend.user.dto.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.save(userRequest);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).build();
    }
}
