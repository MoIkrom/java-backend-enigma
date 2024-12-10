package livecode_smart_class.livecode_smart_class.models.entity;

import jakarta.persistence.*;
import livecode_smart_class.livecode_smart_class.constants.ConstantTable;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = ConstantTable.COURSES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "code")
    private String code;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id", referencedColumnName = "id")
    private User user;

    @Column(name = "level")
    private String level;

    @Column(name = "price")
    private Integer price;

    @Column(name = "created_at")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(
//            shape = JsonFormat.Shape.STRING,
//            pattern = "yyyy-MM-dd"
//    )
    private LocalDateTime created_at;
}