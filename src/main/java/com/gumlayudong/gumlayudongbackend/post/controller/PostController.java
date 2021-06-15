package com.gumlayudong.gumlayudongbackend.post.controller;

import com.gumlayudong.gumlayudongbackend.post.dto.PostRequest;
import com.gumlayudong.gumlayudongbackend.post.dto.PostResponse;
import com.gumlayudong.gumlayudongbackend.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PostRequest postRequest) {
        PostResponse post = postService.save(postRequest);
        return ResponseEntity.created(URI.create("/api/posts/" + post.getId())).build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody PostRequest postRequest) {
        postService.update(postRequest);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<PostResponse> find(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
