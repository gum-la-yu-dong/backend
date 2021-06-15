package com.gumlayudong.gumlayudongbackend.user.controller;

import com.gumlayudong.gumlayudongbackend.user.application.UserService;
import com.gumlayudong.gumlayudongbackend.user.dto.UserRequest;
import com.gumlayudong.gumlayudongbackend.user.dto.UserResponse;
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
    public ResponseEntity<Void> signUp(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.save(userRequest);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).build();
    }

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@RequestParam String email) {
        UserResponse response = userService.findUserByEmail(email);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody UserRequest userRequest) {
        userService.update(userRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestParam String email) {
        userService.deleteByEmail(email);
        return ResponseEntity.noContent().build();
    }
}