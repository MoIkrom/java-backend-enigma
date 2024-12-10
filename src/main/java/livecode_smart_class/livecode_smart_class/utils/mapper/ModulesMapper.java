package livecode_smart_class.livecode_smart_class.utils.mapper;

import livecode_smart_class.livecode_smart_class.models.dto.response.ModulesResponse;
import livecode_smart_class.livecode_smart_class.models.entity.Modules;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Named("ModuleMapper")
public interface ModulesMapper {

    @Mapping(source = "course", target = "course_id")
    @Mapping(source = "course.user.id", target = "course_id.instructor_id.id")
    ModulesResponse toResponse(Modules modules);

    Modules toEntity(ModulesResponse modulesResponse);

    List<ModulesResponse> toResponseList(List<Modules> modules);
}
