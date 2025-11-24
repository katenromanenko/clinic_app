package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto create(AppointmentDto dto);

    AppointmentDto getById(UUID id);

    List<AppointmentDto> getAll();

    AppointmentDto update(UUID id, AppointmentDto dto);

    void delete(UUID id);
}

