package livecode_smart_class.livecode_smart_class.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseRequest {
    private String id;
    @NotBlank(message = "title is Required !")
    private String title;
    private String description;
    @NotBlank(message = "instructor_id is Required !")
    @JsonProperty("instructor_id")
    private String instructorId;
    private String level;
    private Integer price;
}
