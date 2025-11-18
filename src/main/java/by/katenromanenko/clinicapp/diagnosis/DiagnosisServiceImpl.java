package by.katenromanenko.clinicapp.diagnosis;

import java.util.List;
import java.util.UUID;

public class DiagnosisServiceImpl implements DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;

    public DiagnosisServiceImpl(DiagnosisRepository diagnosisRepository) {
        this.diagnosisRepository = diagnosisRepository;
    }

    @Override
    public Diagnosis create(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public Diagnosis getById(UUID id) {
        return diagnosisRepository.findById(id).orElse(null);
    }

    @Override
    public List<Diagnosis> getAll() {
        return diagnosisRepository.findAll();
    }

    @Override
    public Diagnosis update(UUID id, Diagnosis diagnosis) {
        diagnosis.setRecordId(id);
        return diagnosisRepository.save(diagnosis);
    }

    @Override
    public void delete(UUID id) {
        diagnosisRepository.deleteById(id);
    }
}
