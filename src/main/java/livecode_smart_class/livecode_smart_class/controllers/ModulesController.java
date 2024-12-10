package livecode_smart_class.livecode_smart_class.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import livecode_smart_class.livecode_smart_class.models.dto.request.ModulesRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CommonResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.ModulesResponse;
import livecode_smart_class.livecode_smart_class.services.ModulesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/modules")
@RequiredArgsConstructor
public class ModulesController {
    private final ModulesService modulesService;


    @Autowired
    private Validator validator;

    @GetMapping
    public ResponseEntity<CommonResponse<List<ModulesResponse>>> getAllModule(String module) {
        List<ModulesResponse> modulesRespons = modulesService.findAll(module);

        CommonResponse<List<ModulesResponse>> response = CommonResponse.<List<ModulesResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Module Found !")
                .data(modulesRespons)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping
    public ResponseEntity<CommonResponse<ModulesResponse>> createNewModule(@RequestBody ModulesRequest payload) {

        Set<ConstraintViolation<ModulesRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ModulesResponse modulesResponse = modulesService.create(payload);

        CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Module Added !")
                .data(modulesResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ModulesResponse>> getCourseById(@PathVariable String id) {

        ModulesResponse modulesResponse = modulesService.getById(id);

        // Jika Module ditemukan
        CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Module Found !")
                .data(modulesResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ModulesResponse>> deleteCourse(@PathVariable String id) {

        modulesService.deleteById(id);

        CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Module Has been Deleted !")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<ModulesResponse>> updateModule(@PathVariable String id, @RequestBody ModulesRequest payload) {

        Set<ConstraintViolation<ModulesRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        ModulesResponse modulesResponse = modulesService.update(payload);

        CommonResponse<ModulesResponse> response = CommonResponse.<ModulesResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Module !")
                .data(modulesResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
