package livecode_smart_class.livecode_smart_class.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ModulesRequest {
    private String id;
    @JsonProperty("course_id")
    private String courseId;
    @NotBlank(message = "title is Required !")
    private String title;
    private String description;
    private Integer sequenceOrder;

}