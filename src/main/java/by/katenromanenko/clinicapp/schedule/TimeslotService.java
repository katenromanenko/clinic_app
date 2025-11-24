package by.katenromanenko.clinicapp.schedule;

import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;

import java.util.List;
import java.util.UUID;

public interface TimeslotService {

    TimeslotDto create(TimeslotDto dto);

    TimeslotDto getById(UUID id);

    List<TimeslotDto> getAll();

    TimeslotDto update(UUID id, TimeslotDto dto);

    void delete(UUID id);
}

