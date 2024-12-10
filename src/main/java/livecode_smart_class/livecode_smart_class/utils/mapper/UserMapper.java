package livecode_smart_class.livecode_smart_class.utils.mapper;

import java.util.Date;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;
import livecode_smart_class.livecode_smart_class.models.entity.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Named("UserMapper")
public interface UserMapper {
//    @Mapping(target = "created_at", expression = "java(formatDate(user.getCreated_at()))")
//    default String formatDate(Date date) {
//        if (date == null)
//            return null;
//        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yy-MM-dd");
//        return formatter.format(date);
//    }

    UserResponse toResponse(User user);
    User toEntity(UserResponse userResponse);
    List<UserResponse> toResponseList(List<User> user);

}
