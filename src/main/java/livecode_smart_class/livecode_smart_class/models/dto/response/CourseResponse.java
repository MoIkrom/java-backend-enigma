package livecode_smart_class.livecode_smart_class.models.dto.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CourseResponse {
    private String id;
    private String code;
    private String title;
    private String description;
    private UserResponse instructor_id;
    private String level;
    private BigDecimal price;
    private LocalDateTime created_at;
}
