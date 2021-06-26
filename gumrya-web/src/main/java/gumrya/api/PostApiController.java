package gumrya.api;

import gumrya.dto.PostRequest;
import gumrya.dto.PostResponse;
import gumrya.exception.CommonException;
import gumrya.exception.ExceptionResponse;
import gumrya.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApiController {
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

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> commonException(CommonException commonException) {
        String message = commonException.getMessage();
        return ResponseEntity.badRequest().body(new ExceptionResponse(message));
    }
}
