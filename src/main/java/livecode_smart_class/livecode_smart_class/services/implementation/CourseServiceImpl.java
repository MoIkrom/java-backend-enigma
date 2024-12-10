package livecode_smart_class.livecode_smart_class.services.implementation;

import livecode_smart_class.livecode_smart_class.models.dto.request.CourseRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;
import livecode_smart_class.livecode_smart_class.models.entity.Course;
import livecode_smart_class.livecode_smart_class.models.entity.User;
import livecode_smart_class.livecode_smart_class.repositories.CourseRepository;
import livecode_smart_class.livecode_smart_class.repositories.CourseRepository;
import livecode_smart_class.livecode_smart_class.repositories.UserRepository;
import livecode_smart_class.livecode_smart_class.services.CourseService;
import livecode_smart_class.livecode_smart_class.services.CourseService;
import livecode_smart_class.livecode_smart_class.utils.exception.ResourceNotFoundException;
import livecode_smart_class.livecode_smart_class.utils.exception.ValidationException;
import livecode_smart_class.livecode_smart_class.utils.mapper.CourseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final CourseMapper courseMapper;

    private static String generateCode() {
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int randomNum = new Random().nextInt(900) + 100; // Angka 3 digit
        return "COURSE-" + date + "-" + randomNum;
    }

    @Override
    public CourseResponse create(CourseRequest payload) {
        // //Check Duplicate Title
        if (courseRepository.existsByTitle(payload.getTitle())) {
            throw new ValidationException("Coursename already exists!",
                    null);
        }
        try {
            User instructor = userRepository.findById(payload.getInstructorId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Instructor ID not found", null));
            Course course = Course.builder()
                    .title(payload.getTitle())
                    .description(payload.getDescription())
                    .code(generateCode())
                    .user(instructor)
                    .level(payload.getLevel())
                    .price(payload.getPrice())
                    .created_at(Instant.now()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime())
                    .build();

            // Simpan ke database
            courseRepository.save(course);

            UserResponse instructorResponse = UserResponse.builder()
                    .id(instructor.getId())
                    .email(instructor.getEmail())
                    .username(instructor.getUsername())
                    .created_at(instructor.getCreated_at())
                    .role(instructor.getRole())
                    .fullname(instructor.getFullname())
                    .build();

            CourseResponse.CourseResponseBuilder coursesResponse = CourseResponse.builder();
            coursesResponse.id(course.getId());
            coursesResponse.code(course.getCode());
            coursesResponse.title(course.getTitle());
            coursesResponse.description(course.getDescription());
            coursesResponse.instructor_id(instructorResponse);
            coursesResponse.level(course.getLevel());
            coursesResponse.price(BigDecimal.valueOf(course.getPrice()));
            coursesResponse.created_at(course.getCreated_at());
            return coursesResponse.build();

        } catch (Exception ex) {
//            log.error("Error creating course" + ex.getMessage());
            throw new RuntimeException("Error creating course" + ex.getMessage(), ex);
        }

    }

    @Override
    public List<CourseResponse> findAll(String course) {
        // Optionally filter products by name
        if (course != null) {
            return courseRepository
                    .findByTitleJPQL("%" + course + "%")
                    .stream()
                    .map(courseMapper::toResponse)
                    .toList();

        }
        List<CourseResponse> courses = courseRepository.findAllWithInstructors()
                .stream()
                .map(courseMapper::toResponse)
                .toList();

        if (courses.isEmpty()) {
            throw new ResourceNotFoundException("Course not found!", null);
        }

        return courses;
    }

    @Override
    public CourseResponse update(CourseRequest payload) {
        Course course = findOrThrowCourse(payload.getId());

        // //Check Duplicate Coursename
        if (courseRepository.existsByTitle(payload.getTitle())) {
            throw new ValidationException("Coursename already exists!",
                    null);
        }

        // Update Patch Process
        if (payload.getTitle() != null) course.setTitle(payload.getTitle());
        if (payload.getDescription() != null) course.setDescription(payload.getDescription());
        if (payload.getLevel() != null) course.setLevel(payload.getLevel());
//        if (payload.getInstructrId() != null) course.set(payload.getRole());
        if (payload.getPrice() != null) course.setPrice(payload.getPrice());

        course = courseRepository.saveAndFlush(course);
        return courseMapper.toResponse(course);
    }

    private Course findOrThrowCourse(String id) {
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found", null));

    }

    @Override
    public CourseResponse getById(String id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course not found", null));
        return courseMapper.toResponse(course);
    }

    @Override
    public CourseResponse deleteById(String id) {
        // Cari Course berdasarkan ID
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course with ID: " + id + " Not Found !", null));

        // Hapus produk dari database
        courseRepository.delete(course);

        // Kembalikan response mapper dari produk yang dihapus
        courseMapper.toResponse(course);
        return null;
    }
}
