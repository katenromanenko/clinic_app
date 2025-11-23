package by.katenromanenko.clinicapp.user;

import by.katenromanenko.clinicapp.user.dto.AppUserDto;

import java.util.List;
import java.util.UUID;

public interface AppUserService {

    AppUserDto create(AppUserDto dto);

    AppUserDto getById(UUID id);

    List<AppUserDto> getAll();

    AppUserDto update(UUID id, AppUserDto dto);

    void delete(UUID id);

    AppUserDto activate(UUID id);

    AppUserDto deactivate(UUID id);
}
