package livecode_smart_class.livecode_smart_class.models.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserResponse {
    private String id;
    private String username;
    private String email;
    private String fullname;
    private String role;
    private LocalDateTime created_at;
}
