package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import by.katenromanenko.clinicapp.appointment.mapper.AppointmentMapper;
import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.schedule.TimeslotRepository;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AppUserRepository appUserRepository;
    private final TimeslotRepository timeslotRepository;
    private final AppointmentMapper appointmentMapper;

    @Override
    public AppointmentDto create(AppointmentDto dto) {
        Appointment entity = appointmentMapper.toEntity(dto);

        entity.setAppointmentId(UUID.randomUUID());

        AppUser patient = appUserRepository.findById(dto.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Пациент не найден: " + dto.getPatientId()));
        entity.setPatient(patient);

        AppUser doctor = appUserRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Доктор не найден: " + dto.getDoctorId()));
        entity.setDoctor(doctor);

        if (dto.getSlotId() != null) {
            Timeslot slot = timeslotRepository.findById(dto.getSlotId())
                    .orElseThrow(() -> new IllegalArgumentException("Слот не найден: " + dto.getSlotId()));
            entity.setSlot(slot);
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);
        entity.setCanceledAt(null);
        entity.setNotificationSent(false);

        Appointment saved = appointmentRepository.save(entity);

        return appointmentMapper.toDto(saved);
    }

    @Override
    public AppointmentDto getById(UUID id) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Приём не найден: " + id));
        return appointmentMapper.toDto(entity);
    }

    @Override
    public List<AppointmentDto> getAll() {
        List<Appointment> entities = appointmentRepository.findAll();
        return appointmentMapper.toDtoList(entities);
    }

    @Override
    public AppointmentDto update(UUID id, AppointmentDto dto) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Приём не найден: " + id));

        if (dto.getStartAt() != null) {
            entity.setStartAt(dto.getStartAt());
        }
        if (dto.getDurationMin() != null) {
            entity.setDurationMin(dto.getDurationMin());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getCancellationReason() != null) {
            entity.setCancellationReason(dto.getCancellationReason());
        }

        entity.setNotificationSent(dto.isNotificationSent());

        if (dto.getPatientId() != null) {
            AppUser patient = appUserRepository.findById(dto.getPatientId())
                    .orElseThrow(() -> new IllegalArgumentException("Пациент не найден: " + dto.getPatientId()));
            entity.setPatient(patient);
        }

        if (dto.getDoctorId() != null) {
            AppUser doctor = appUserRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new IllegalArgumentException("Доктор не найден: " + dto.getDoctorId()));
            entity.setDoctor(doctor);
        }

        if (dto.getSlotId() != null) {
            Timeslot slot = timeslotRepository.findById(dto.getSlotId())
                    .orElseThrow(() -> new IllegalArgumentException("Слот не найден: " + dto.getSlotId()));
            entity.setSlot(slot);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        if (entity.getStatus() == AppointmentStatus.CANCELED) {
            entity.setCanceledAt(LocalDateTime.now());
        }

        Appointment saved = appointmentRepository.save(entity);
        return appointmentMapper.toDto(saved);
    }

    @Override
    public void delete(UUID id) {
        appointmentRepository.deleteById(id);
    }
}
