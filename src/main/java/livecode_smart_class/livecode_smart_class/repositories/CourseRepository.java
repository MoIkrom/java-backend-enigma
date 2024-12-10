package livecode_smart_class.livecode_smart_class.repositories;


import jakarta.validation.constraints.NotBlank;
import livecode_smart_class.livecode_smart_class.models.entity.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

    boolean existsByTitle(@NotBlank(message = "Title is Required !") String title);

    // Custom query JPQL
    @Query("SELECT c FROM Course c WHERE c.title LIKE :title ORDER BY c.title DESC")
    List<Course> findByTitleJPQL(@Param("title") String title);

//    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.instructor_id WHERE c.id = :id")
//    Optional<Course> findByIdWithInstructor(@Param("id") String id);

    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.user")
    List<Course> findAllWithInstructors();
}
