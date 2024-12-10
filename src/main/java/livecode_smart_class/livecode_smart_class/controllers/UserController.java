package livecode_smart_class.livecode_smart_class.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import livecode_smart_class.livecode_smart_class.models.dto.request.UserRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CommonResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;
import livecode_smart_class.livecode_smart_class.services.UserService;
import livecode_smart_class.livecode_smart_class.utils.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @Autowired
    private Validator validator;

    @GetMapping
    public ResponseEntity<CommonResponse<List<UserResponse>>> getAllUser(String username) {
        List<UserResponse> customerResponses = userService.findAll(username);

        CommonResponse<List<UserResponse>> response = CommonResponse.<List<UserResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Found !")
                .data(customerResponses)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping
    public ResponseEntity<CommonResponse<UserResponse>> createNewUser(@RequestBody UserRequest payload) {

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserResponse userResponse = userService.create(payload);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Customer Added !")
                .data(userResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> getUserById(@PathVariable String id) {

        UserResponse userResponse = userService.getById(id);

        // Jika Customer ditemukan

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Found !")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> deleteUser(@PathVariable String id) {

        userService.deleteById(id);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("User Has been Deleted !")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<UserResponse>> updateUser(@PathVariable String id, @RequestBody UserRequest payload) {

        Set<ConstraintViolation<UserRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserResponse userResponse = userService.update(payload);

        CommonResponse<UserResponse> response = CommonResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Update User !")
                .data(userResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
