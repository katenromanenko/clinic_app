package by.katenromanenko.clinicapp.user;

import by.katenromanenko.clinicapp.common.error.NotFoundException;
import by.katenromanenko.clinicapp.specialization.Specialization;
import by.katenromanenko.clinicapp.specialization.SpecializationRepository;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import by.katenromanenko.clinicapp.user.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserDto create(AppUserDto dto) {

        AppUser entity = appUserMapper.toEntity(dto);

        if (entity.getUserId() == null) {
            entity.setUserId(UUID.randomUUID());
        }

        String rawOrHash = entity.getPasswordHash();

        if (rawOrHash == null || rawOrHash.isBlank()) {
            throw new IllegalArgumentException("passwordHash обязателен");
        }

        if (!isBcryptHash(rawOrHash)) {
            entity.setPasswordHash(passwordEncoder.encode(rawOrHash));
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);
        entity.setActive(true);

        if (dto.getSpecializationId() != null) {
            Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                    .orElseThrow(() -> new NotFoundException(
                            "Специализация не найдена: " + dto.getSpecializationId()
                    ));
            entity.setSpecialization(specialization);
        } else {
            entity.setSpecialization(null);
        }

        AppUser saved = appUserRepository.save(entity);
        return appUserMapper.toDto(saved);
    }

    @Override
    public AppUserDto getById(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));

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
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));

        AppUser entity = appUserMapper.toEntity(dto);

        entity.setUserId(id);
        entity.setCreatedAt(existing.getCreatedAt());
        entity.setUpdatedAt(LocalDateTime.now());

        entity.setActive(existing.isActive());

        // 3) пароль:
        //    - если не прислали — оставляем старый
        //    - если прислали — проверим и (если надо) захэшируем
        String newRawOrHash = entity.getPasswordHash();

        if (newRawOrHash == null || newRawOrHash.isBlank()) {
            entity.setPasswordHash(existing.getPasswordHash());
        } else {
            if (!isBcryptHash(newRawOrHash)) {
                entity.setPasswordHash(passwordEncoder.encode(newRawOrHash));
            }
        }

        if (dto.getSpecializationId() != null) {
            Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                    .orElseThrow(() -> new NotFoundException(
                            "Специализация не найдена: " + dto.getSpecializationId()
                    ));
            entity.setSpecialization(specialization);
        } else {
            entity.setSpecialization(existing.getSpecialization());
        }

        AppUser saved = appUserRepository.save(entity);
        return appUserMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {

        boolean exists = appUserRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Пользователь не найден: " + id);
        }

        appUserRepository.deleteById(id);
    }

    @Override
    public AppUserDto activate(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));

        entity.setActive(true);
        entity.setUpdatedAt(LocalDateTime.now());

        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    @Override
    public AppUserDto deactivate(UUID id) {
        AppUser entity = appUserRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден: " + id));

        entity.setActive(false);
        entity.setUpdatedAt(LocalDateTime.now());

        return appUserMapper.toDto(appUserRepository.save(entity));
    }

    private boolean isBcryptHash(String value) {
        return value.startsWith("$2a$") || value.startsWith("$2b$") || value.startsWith("$2y$");
    }
}
