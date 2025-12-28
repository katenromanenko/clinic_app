package by.katenromanenko.clinicapp.specialization;

import by.katenromanenko.clinicapp.common.error.NotFoundException;
import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;
import by.katenromanenko.clinicapp.specialization.mapper.SpecializationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SpecializationServiceImpl implements SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SpecializationMapper specializationMapper;

    @Override
    public SpecializationDto create(SpecializationDto dto) {

        Specialization entity = specializationMapper.toEntity(dto);

        entity.setSpecId(UUID.randomUUID());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);

        Specialization saved = specializationRepository.save(entity);
        return specializationMapper.toDto(saved);
    }

    @Override
    public SpecializationDto getById(UUID id) {
        Specialization entity = specializationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Специализация не найдена: " + id));
        return specializationMapper.toDto(entity);
    }

    @Override
    public List<SpecializationDto> getAll() {
        List<Specialization> entities = specializationRepository.findAll();
        return specializationMapper.toDtoList(entities);
    }

    @Override
    public SpecializationDto update(UUID id, SpecializationDto dto) {
        Specialization existing = specializationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Специализация не найдена: " + id));

        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setUpdatedAt(LocalDateTime.now());

        Specialization saved = specializationRepository.save(existing);
        return specializationMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {

        boolean exists = specializationRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Специализация не найдена: " + id);
        }

        specializationRepository.deleteById(id);
    }
}
