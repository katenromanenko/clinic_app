package by.katenromanenko.clinicapp.specialization;

import java.util.List;
import java.util.UUID;

public class SpecializationServiceImpl  implements SpecializationService{


    private final SpecializationRepository specializationRepository;

    public SpecializationServiceImpl(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    @Override
    public Specialization create(Specialization specialization) {
        return specializationRepository.save(specialization);
    }

    @Override
    public Specialization getById(UUID id) {
        return specializationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Specialization> getAll() {
        return specializationRepository.findAll();
    }

    @Override
    public Specialization update(UUID id, Specialization specialization) {
        specialization.setSpecId(id);
        return specializationRepository.save(specialization);
    }

    @Override
    public void delete(UUID id) {
        specializationRepository.deleteById(id);
    }
}

