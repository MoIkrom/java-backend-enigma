package livecode_smart_class.livecode_smart_class.models.entity;

import jakarta.persistence.*;
import livecode_smart_class.livecode_smart_class.constants.ConstantTable;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = ConstantTable.MODULES)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Modules {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "sequence_order")
    private Integer sequenceOrder;

    @Column(name = "created_at")
//    @Temporal(TemporalType.DATE)
//    @JsonFormat(
//            shape = JsonFormat.Shape.STRING,
//            pattern = "yyyy-MM-dd"
//    )
    private LocalDateTime created_at;
}
