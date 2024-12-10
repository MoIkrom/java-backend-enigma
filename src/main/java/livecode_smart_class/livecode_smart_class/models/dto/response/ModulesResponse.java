package livecode_smart_class.livecode_smart_class.models.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ModulesResponse {
    private String id;
    private CourseResponse course_id;
    private String title;
    private String description;
    private Integer sequenceOrder;
    private LocalDateTime created_at;
}
