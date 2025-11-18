package by.katenromanenko.clinicapp.specialization;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpecializationRepository extends JpaRepository<Specialization, UUID> {
}
