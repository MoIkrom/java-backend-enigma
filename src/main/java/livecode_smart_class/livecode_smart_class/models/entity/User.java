package livecode_smart_class.livecode_smart_class.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import livecode_smart_class.livecode_smart_class.constants.ConstantTable;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = ConstantTable.USERS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullname;

    @Column(name = "role")
    private String role;

    @Column(name = "created_at")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(
//            shape = JsonFormat.Shape.STRING,
//            pattern = "yyyy-MM-dd"
//    )
    private LocalDateTime created_at;
}