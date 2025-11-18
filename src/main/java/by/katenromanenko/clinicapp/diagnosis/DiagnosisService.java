package by.katenromanenko.clinicapp.diagnosis;

import java.util.List;
import java.util.UUID;

public interface DiagnosisService {

    Diagnosis create(Diagnosis diagnosis);

    Diagnosis getById(UUID id);

    List<Diagnosis> getAll();

    Diagnosis update(UUID id, Diagnosis diagnosis);

    void delete(UUID id);
}
