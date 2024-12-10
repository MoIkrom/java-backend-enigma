package livecode_smart_class.livecode_smart_class.utils.mapper;

import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.models.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Named("CourseMapper")
public interface CourseMapper {

    @Mapping(source = "user.id" , target = "instructor_id.id")
    CourseResponse toResponse(Course course);
    Course toEntity(CourseResponse courseResponse);
    List<CourseResponse> toResponseList(List<Course> courses);

}
