package livecode_smart_class.livecode_smart_class.repositories;


import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import livecode_smart_class.livecode_smart_class.models.entity.Modules;

import java.util.List;

@Repository
public interface ModulesRepository extends JpaRepository<Modules, String> {

    boolean existsByTitle(@NotBlank(message = "Title is Required !") String title);

    // Custom query JPQL
    @Query("SELECT m FROM Modules m LEFT JOIN FETCH m.course WHERE m.title LIKE :title ORDER BY m.title DESC")
    List<Modules> findByTitlesJPQL(@Param("title") String title);

    @Query("SELECT m FROM Modules m LEFT JOIN FETCH m.course")
    List<Modules> findAllWithCourse();
}
