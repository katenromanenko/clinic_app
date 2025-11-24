package by.katenromanenko.clinicapp.diagnosis;

import by.katenromanenko.clinicapp.diagnosis.dto.DiagnosisDto;

import java.util.List;
import java.util.UUID;

public interface DiagnosisService {

    DiagnosisDto create(DiagnosisDto dto);

    DiagnosisDto getById(UUID id);

    List<DiagnosisDto> getAll();

    DiagnosisDto update(UUID id, DiagnosisDto dto);

    void delete(UUID id);
}
