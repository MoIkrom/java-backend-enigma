package livecode_smart_class.livecode_smart_class.models.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.Date;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserRequest {
    private String id;
    @NotBlank(message = "Username is Required !")
    private String username;
    @NotBlank(message = "Email is Required !")
    private String email;
    @NotBlank(message = "Fullname is Required !")
    private String fullname;
    private String role;
}
