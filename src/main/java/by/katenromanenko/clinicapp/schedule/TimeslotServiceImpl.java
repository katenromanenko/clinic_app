package by.katenromanenko.clinicapp.schedule;

import by.katenromanenko.clinicapp.common.error.NotFoundException;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import by.katenromanenko.clinicapp.schedule.mapper.TimeslotMapper;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TimeslotServiceImpl implements TimeslotService {

    private final TimeslotRepository timeslotRepository;
    private final AppUserRepository appUserRepository;
    private final TimeslotMapper timeslotMapper;

    @Override
    public TimeslotDto create(TimeslotDto dto) {

        String login = currentLogin();

        AppUser currentUser = appUserRepository.findByLogin(login)
                .orElseThrow(() -> new NotFoundException(
                        "Пользователь не найден по login: " + login
                ));

        AppUser doctor;

        switch (currentUser.getRole()) {

            case DOCTOR -> {
                doctor = currentUser;

                if (dto.getDoctorId() != null
                        && !dto.getDoctorId().equals(doctor.getUserId())) {
                    throw new IllegalArgumentException(
                            "Доктор не может создавать таймслоты для другого врача"
                    );
                }
            }

            case ADMIN -> {
                if (dto.getDoctorId() == null) {
                    throw new IllegalArgumentException(
                            "Для ADMIN поле doctorId обязательно"
                    );
                }

                doctor = appUserRepository.findById(dto.getDoctorId())
                        .orElseThrow(() -> new NotFoundException(
                                "Доктор не найден: " + dto.getDoctorId()
                        ));
            }

            default -> throw new IllegalArgumentException(
                    "Только DOCTOR или ADMIN могут создавать таймслоты"
            );
        }

        Timeslot entity = timeslotMapper.toEntity(dto);
        entity.setSlotId(UUID.randomUUID());
        entity.setDoctor(doctor);

        if (entity.getState() == null) {
            entity.setState(TimeslotState.AVAILABLE);
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);

        Timeslot saved = timeslotRepository.save(entity);
        return timeslotMapper.toDto(saved);
    }


    @Override
    public TimeslotDto getById(UUID id) {
        Timeslot entity = timeslotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Таймслот не найден: " + id));
        return timeslotMapper.toDto(entity);
    }

    @Override
    public List<TimeslotDto> getAll() {
        List<Timeslot> entities = timeslotRepository.findAll();
        return timeslotMapper.toDtoList(entities);
    }

    @Override
    public TimeslotDto update(UUID id, TimeslotDto dto) {
        Timeslot entity = timeslotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Таймслот не найден: " + id));

        if (dto.getStartTime() != null) {
            entity.setStartTime(dto.getStartTime());
        }
        if (dto.getEndTime() != null) {
            entity.setEndTime(dto.getEndTime());
        }
        if (dto.getState() != null) {
            entity.setState(dto.getState());
        }

        entity.setBlocked(dto.isBlocked());

        if (dto.getDoctorId() != null) {
            AppUser doctor = appUserRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new NotFoundException("Доктор не найден: " + dto.getDoctorId()));
            entity.setDoctor(doctor);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        Timeslot saved = timeslotRepository.save(entity);
        return timeslotMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {

        boolean exists = timeslotRepository.existsById(id);
        if (!exists) {
            throw new NotFoundException("Таймслот не найден: " + id);
        }

        timeslotRepository.deleteById(id);
    }

    private String currentLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("Нет аутентификации в SecurityContext");
        }
        return auth.getName();
    }
}

