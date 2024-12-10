package livecode_smart_class.livecode_smart_class.services;

import livecode_smart_class.livecode_smart_class.models.dto.request.CourseRequest;
import livecode_smart_class.livecode_smart_class.models.dto.request.UserRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;

import java.util.List;

public interface CourseService {
    CourseResponse create(CourseRequest payload);

    List<CourseResponse> findAll(String title);

    CourseResponse update(CourseRequest payload);

    CourseResponse getById(String id);

    CourseResponse deleteById(String id);
}
