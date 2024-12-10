package livecode_smart_class.livecode_smart_class.services;

import livecode_smart_class.livecode_smart_class.models.dto.request.UserRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(UserRequest payload);

    List<UserResponse> findAll(String username);

    UserResponse update(UserRequest payload);

    UserResponse getById(String id);

    UserResponse deleteById(String id);
}
