package by.katenromanenko.clinicapp.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TimeslotRepository extends JpaRepository<Timeslot, UUID> {
}
