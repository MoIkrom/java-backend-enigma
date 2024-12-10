package livecode_smart_class.livecode_smart_class.services;

import livecode_smart_class.livecode_smart_class.models.dto.request.ModulesRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.ModulesResponse;

import java.util.List;

public interface ModulesService {
    ModulesResponse create(ModulesRequest payload);

    List<ModulesResponse> findAll(String title);

    ModulesResponse update(ModulesRequest payload);

    ModulesResponse getById(String id);

    ModulesResponse deleteById(String id);
}
