package by.katenromanenko.clinicapp.diagnosis;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DiagnosisRepository extends JpaRepository<Diagnosis, UUID> {
}
