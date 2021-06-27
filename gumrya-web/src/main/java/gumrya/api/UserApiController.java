package gumrya.api;

import gumrya.dto.UserRequest;
import gumrya.dto.UserResponse;
import gumrya.exception.CommonException;
import gumrya.exception.ExceptionResponse;
import gumrya.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.save(userRequest);
        return ResponseEntity.created(URI.create("/api/users/" + user.getId())).build();
    }

    @GetMapping
    public ResponseEntity<UserResponse> findUser(@RequestParam String email) {
        UserResponse response = userService.findByEmail(email);
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

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> commonException(CommonException commonException) {
        String message = commonException.getMessage();
        return ResponseEntity.badRequest().body(new ExceptionResponse(message));
    }
}
