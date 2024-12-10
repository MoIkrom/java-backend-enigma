package livecode_smart_class.livecode_smart_class.controllers;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import livecode_smart_class.livecode_smart_class.models.dto.request.CourseRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CommonResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;


    @Autowired
    private Validator validator;

    @GetMapping
    public ResponseEntity<CommonResponse<List<CourseResponse>>> getAllCourse(String course) {
        List<CourseResponse> courseResponses = courseService.findAll(course);

        CommonResponse<List<CourseResponse>> response = CommonResponse.<List<CourseResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Course Found !")
                .data(courseResponses)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }

    @PostMapping
    public ResponseEntity<CommonResponse<CourseResponse>> createNewCourse(@RequestBody CourseRequest payload) {

        Set<ConstraintViolation<CourseRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CourseResponse courseResponse = courseService.create(payload);

        CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("New Customer Added !")
                .data(courseResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseResponse>> getCourseById(@PathVariable String id) {

        CourseResponse courseResponse = courseService.getById(id);

        // Jika Course ditemukan
        CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Course Found !")
                .data(courseResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseResponse>> deleteCourse(@PathVariable String id) {

        courseService.deleteById(id);

        CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Course Has been Deleted !")
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse<CourseResponse>> updateCourse(@PathVariable String id, @RequestBody CourseRequest payload) {

        Set<ConstraintViolation<CourseRequest>> violations = validator.validate(payload);
        if (!violations.isEmpty()) {

            // Ambil pesan kesalahan pertama dari validasi
            String errorMessage = violations.iterator().next().getMessage();

            // Buat CommonResponse untuk menampilkan pesan error
            CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .message(errorMessage)
                    .build();

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        CourseResponse courseResponse = courseService.update(payload);

        CommonResponse<CourseResponse> response = CommonResponse.<CourseResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success Update Course !")
                .data(courseResponse)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

}
