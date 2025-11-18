package by.katenromanenko.clinicapp.specialization;

import java.util.List;
import java.util.UUID;

public interface SpecializationService {

    Specialization create(Specialization specialization);

    Specialization getById(UUID id);

    List<Specialization> getAll();

    Specialization update(UUID id, Specialization specialization);

    void delete(UUID id);

}
