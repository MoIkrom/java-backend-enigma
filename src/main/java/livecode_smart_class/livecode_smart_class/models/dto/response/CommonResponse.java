package livecode_smart_class.livecode_smart_class.models.dto.response;

import lombok.*;

@Getter
@Builder
public class CommonResponse<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
