package by.katenromanenko.clinicapp.schedule;

import java.util.List;
import java.util.UUID;

public interface TimeslotService {

    Timeslot create(Timeslot timeslot);

    Timeslot getById(UUID id);

    List<Timeslot> getAll();

    Timeslot update(UUID id, Timeslot timeslot);

    void delete(UUID id);
}
