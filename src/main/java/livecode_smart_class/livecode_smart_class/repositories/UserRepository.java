package livecode_smart_class.livecode_smart_class.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.validation.constraints.NotBlank;
import livecode_smart_class.livecode_smart_class.models.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByEmail(@NotBlank(message = "Email is Required !") String email);

    boolean existsByUsername(@NotBlank(message = "User Name is Required !") String username);

    // Custom query JPQL
    @Query("SELECT u FROM User u WHERE u.username LIKE :username ORDER BY u.username DESC")
    List<User> findByUsernameJPQL(@Param("username") String username);

}
