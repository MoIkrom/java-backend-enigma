package livecode_smart_class.livecode_smart_class.controllers.exception;

import livecode_smart_class.livecode_smart_class.models.dto.response.CommonResponse;
import livecode_smart_class.livecode_smart_class.utils.exception.ResourceNotFoundException;
import livecode_smart_class.livecode_smart_class.utils.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CommonResponse<String>>
    handlerNotFoundException(ResourceNotFoundException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<CommonResponse<String>>
    handleValidationException(ValidationException ex) {
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.NOT_ACCEPTABLE.value())
                .message(ex.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(response);
    }
}
