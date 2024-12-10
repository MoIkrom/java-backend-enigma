package livecode_smart_class.livecode_smart_class.services.implementation;

import livecode_smart_class.livecode_smart_class.models.dto.request.UserRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;
import livecode_smart_class.livecode_smart_class.models.entity.User;
import livecode_smart_class.livecode_smart_class.repositories.UserRepository;
import livecode_smart_class.livecode_smart_class.services.UserService;
import livecode_smart_class.livecode_smart_class.utils.exception.ResourceNotFoundException;
import livecode_smart_class.livecode_smart_class.utils.exception.ValidationException;
import livecode_smart_class.livecode_smart_class.utils.mapper.UserMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest payload) {
        // //Check Duplicate Username
        if (userRepository.existsByUsername(payload.getUsername())) {
            throw new ValidationException("Username already exists!",
                    null);
        }
        // //Check Duplicate Email
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new ValidationException("User with the same EMAIL already exists!",
                    null);
        }
        User user = User.builder()
                .username(payload.getUsername())
                .email(payload.getEmail())
                .fullname(payload.getFullname())
                .role(payload.getRole())
                .created_at(Instant.now()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();
        // Simpan ke database
        userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public List<UserResponse> findAll(String username) {
        // Optionally filter products by name
        if (username != null) {
            return userRepository
                    .findByUsernameJPQL("%" + username + "%")
                    .stream()
                    .map(userMapper::toResponse)
                    .toList();

        }
        List<UserResponse> users = userRepository.findAll()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        if (users.isEmpty()) {
            throw new ResourceNotFoundException("User not found!", null);
        }

        return users;
    }

    @Override
    public UserResponse update(UserRequest payload) {
        User user = findOrThrowUser(payload.getId());

        // //Check Duplicate Username
        if (userRepository.existsByUsername(payload.getUsername())) {
            throw new ValidationException("Username already exists!",
                    null);
        }
        // //Check Duplicate Email
        if (userRepository.existsByEmail(payload.getEmail())) {
            throw new ValidationException("User with the same EMAIL already exists!",
                    null);
        }

        // Update Patch Process
        if (payload.getUsername() != null) user.setUsername(payload.getUsername());
        if (payload.getEmail() != null) user.setEmail(payload.getEmail());
        if (payload.getFullname() != null) user.setFullname(payload.getFullname());
        if (payload.getRole() != null) user.setRole(payload.getRole());

        user = userRepository.saveAndFlush(user);
        return userMapper.toResponse(user);
    }

    private User findOrThrowUser(String id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found", null));

    }

    @Override
    public UserResponse getById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found", null));
        return userMapper.toResponse(user);
    }

    @Override
    public UserResponse deleteById(String id) {
        // Cari produk berdasarkan ID
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + id + " Not Found !", null));

        // Hapus produk dari database
        userRepository.delete(user);

        // Kembalikan response mapper dari produk yang dihapus
        userMapper.toResponse(user);
        return null;
    }
}
