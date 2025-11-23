package by.katenromanenko.clinicapp.specialization;

import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;

import java.util.List;
import java.util.UUID;

public interface SpecializationService {

    SpecializationDto create(SpecializationDto dto);

    SpecializationDto getById(UUID id);

    List<SpecializationDto> getAll();

    SpecializationDto update(UUID id, SpecializationDto dto);

    void delete(UUID id);
}
