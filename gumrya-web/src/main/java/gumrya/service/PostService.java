package gumrya.service;

import gumrya.dto.PostRequest;
import gumrya.dto.PostResponse;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    public PostResponse save(PostRequest postRequest) {
        return null;
    }

    public void update(PostRequest updateRequest) {
        return;
    }

    public PostResponse findById(long id) {
        return null;
    }

    public void delete(Long id) {
        return;
    }
}
