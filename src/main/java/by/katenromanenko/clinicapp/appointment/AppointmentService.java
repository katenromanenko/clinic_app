package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.appointment.dto.AppointmentCreateRequest;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDto create(AppointmentCreateRequest request);

    AppointmentDto getById(UUID id);

    List<AppointmentDto> getAll();

    AppointmentDto update(UUID id, AppointmentUpdateRequest request);

    void delete(UUID id);
}
