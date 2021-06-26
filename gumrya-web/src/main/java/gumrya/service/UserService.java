package gumrya.service;

import gumrya.dto.UserRequest;
import gumrya.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    public UserResponse save(UserRequest userRequest) {
        return null;
    }

    public void update(UserRequest userRequest) {
    }

    public UserResponse findUserByEmail(String email) {
        return null;
    }

    public void deleteByEmail(String any) {

    }
}
