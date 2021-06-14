package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.user.dto.UserSaveRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody UserSaveRequest userSaveRequest) {
        return ResponseEntity.ok("ok");
    }
}
