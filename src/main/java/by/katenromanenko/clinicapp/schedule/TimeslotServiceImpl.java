package by.katenromanenko.clinicapp.schedule;

import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import by.katenromanenko.clinicapp.schedule.mapper.TimeslotMapper;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
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

        Timeslot entity = timeslotMapper.toEntity(dto);


        entity.setSlotId(UUID.randomUUID());


        AppUser doctor = appUserRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Доктор не найден: " + dto.getDoctorId()));
        entity.setDoctor(doctor);


        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);

        Timeslot saved = timeslotRepository.save(entity);

        return timeslotMapper.toDto(saved);
    }

    @Override
    public TimeslotDto getById(UUID id) {
        Timeslot entity = timeslotRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Таймслот не найден: " + id));
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
                .orElseThrow(() -> new IllegalArgumentException("Таймслот не найден: " + id));

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
                    .orElseThrow(() -> new IllegalArgumentException("Доктор не найден: " + dto.getDoctorId()));
            entity.setDoctor(doctor);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        Timeslot saved = timeslotRepository.save(entity);
        return timeslotMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        timeslotRepository.deleteById(id);
    }
}

