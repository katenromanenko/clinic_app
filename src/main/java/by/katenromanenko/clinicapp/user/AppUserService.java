package by.katenromanenko.clinicapp.user;

import java.util.List;
import java.util.UUID;

public interface AppUserService {
    AppUser create(AppUser user);

    AppUser getById(UUID id);

    List<AppUser> getAll();

    AppUser update(UUID id, AppUser user);

    void delete(UUID id);
}
