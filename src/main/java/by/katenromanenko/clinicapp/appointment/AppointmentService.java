package by.katenromanenko.clinicapp.appointment;

import java.util.UUID;
import java.util.List;

public interface AppointmentService {

    Appointment create(Appointment appointment);

    Appointment getById(UUID id);

    List<Appointment> getAll();

    Appointment update(UUID id, Appointment appointment);

    void delete(UUID id);
}
