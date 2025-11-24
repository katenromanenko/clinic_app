package by.katenromanenko.clinicapp.user;

import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import by.katenromanenko.clinicapp.user.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;

    @Override
    public AppUserDto create(AppUserDto dto) {

        AppUser entity = appUserMapper.toEntity(dto);

        if (entity.getUserId() == null) {
            entity.setUserId(UUID.randomUUID());
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);
        entity.setActive(true);

        AppUser saved = appUserRepository.save(entity);
        return appUserMapper.toDto(saved);
    }

    @Override
    public AppUserDto getById(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));

        return appUserMapper.toDto(entity);
    }

    @Override
    public List<AppUserDto> getAll() {
        List<AppUser> users = appUserRepository.findAll();
        return appUserMapper.toDtoList(users);
    }

    @Override
    public AppUserDto update(UUID id, AppUserDto dto) {
        AppUser existing = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));

        AppUser entity = appUserMapper.toEntity(dto);

        entity.setUserId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());

        if (entity.getPasswordHash() == null) {
            entity.setPasswordHash(existing.getPasswordHash());
        }

        AppUser saved = appUserRepository.save(entity);

        return appUserMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public AppUserDto activate(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));

        entity.setActive(true);
        entity.setUpdatedAt(LocalDateTime.now());

        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Override
    public AppUserDto deactivate(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден: " + id));

        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());

        return appUserMapper.toDto(appUserRepository.save(entity));
    }
}
