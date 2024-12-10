package livecode_smart_class.livecode_smart_class.services.implementation;

import livecode_smart_class.livecode_smart_class.models.dto.request.ModulesRequest;
import livecode_smart_class.livecode_smart_class.models.dto.response.CourseResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.ModulesResponse;
import livecode_smart_class.livecode_smart_class.models.dto.response.UserResponse;
import livecode_smart_class.livecode_smart_class.models.entity.Course;
import livecode_smart_class.livecode_smart_class.models.entity.Modules;
import livecode_smart_class.livecode_smart_class.repositories.CourseRepository;
import livecode_smart_class.livecode_smart_class.repositories.ModulesRepository;
import livecode_smart_class.livecode_smart_class.services.ModulesService;
import livecode_smart_class.livecode_smart_class.utils.exception.ResourceNotFoundException;
import livecode_smart_class.livecode_smart_class.utils.exception.ValidationException;
import livecode_smart_class.livecode_smart_class.utils.mapper.ModulesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModulesServiceImpl implements ModulesService {

    private final ModulesRepository modulesRepository;
    private final CourseRepository courseRepository;
    private final ModulesMapper modulesMapper;


    @Override
    public ModulesResponse create(ModulesRequest payload) {
        // //Check Duplicate Title
        if (modulesRepository.existsByTitle(payload.getTitle())) {
            throw new ValidationException("Module Title already exists!",
                    null);
        }

        Course course = courseRepository.findById(payload.getCourseId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course ID not found", null));

        Modules modules = Modules.builder()
                .title(payload.getTitle())
                .course(course)
                .description(payload.getDescription())
                .sequenceOrder(payload.getSequenceOrder())
                .created_at(Instant.now()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime())
                .build();

        // Simpan ke database
        modulesRepository.save(modules);
        return modulesMapper.toResponse(modules);
    }

    @Override
    public List<ModulesResponse> findAll(String title) {
        // Optionally filter module by name
        if (title != null) {
            return modulesRepository
                    .findByTitlesJPQL("%" + title + "%")
                    .stream()
                    .map(modulesMapper::toResponse)
                    .toList();

        }
        List<ModulesResponse> modules = modulesRepository.findAllWithCourse()
                .stream()
                .map(modulesMapper::toResponse)
                .toList();

        if (modules.isEmpty()) {
            throw new ResourceNotFoundException("Module not found!", null);
        }
        return modules;

    }

    @Override
    public ModulesResponse update(ModulesRequest payload) {
        Modules modules = findOrThrowCourse(payload.getId());
//
//        // //Check Duplicate Coursename
        if (modulesRepository.existsByTitle(payload.getTitle())) {
            throw new ValidationException("Module already exists!",
                    null);
        }

        // Update Patch Process
        if (payload.getTitle() != null) modules.setTitle(payload.getTitle());
        if (payload.getDescription() != null) modules.setDescription(payload.getDescription());

        modules = modulesRepository.saveAndFlush(modules);
        return modulesMapper.toResponse(modules);
    }

    private Modules findOrThrowCourse(String id) {
        return modulesRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Module not found", null));

    }

    @Override
    public ModulesResponse getById(String id) {
        Modules modules = modulesRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Module not found", null));
        return modulesMapper.toResponse(modules);
    }

    @Override
    public ModulesResponse deleteById(String id) {
        // Cari Course berdasarkan ID
        Modules modules = modulesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module with ID: " + id + " Not Found !", null));

        // Hapus produk dari database
        modulesRepository.delete(modules);

        // Kembalikan response mapper dari produk yang dihapus
        return modulesMapper.toResponse(modules);
    }
}
