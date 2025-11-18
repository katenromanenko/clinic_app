package by.katenromanenko.clinicapp.user;

import java.util.List;
import java.util.UUID;

public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    public AppUserServiceImpl(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public AppUser create(AppUser user) {
        return appUserRepository.save(user);
    }

    @Override
    public AppUser getById(UUID id) {
        return appUserRepository.findById(id).orElse(null);
    }

    @Override
    public List<AppUser> getAll() {
        return appUserRepository.findAll();
    }

    @Override
    public AppUser update(UUID id, AppUser user) {
        user.setUserId(id);            // выставляем id, который обновляем
        return appUserRepository.save(user);
    }

    @Override
    public void delete(UUID id) {
        appUserRepository.deleteById(id);
    }
}
