package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.appointment.dto.AppointmentCreateRequest;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentUpdateRequest;
import by.katenromanenko.clinicapp.appointment.mapper.AppointmentMapper;
import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.schedule.TimeslotRepository;
import by.katenromanenko.clinicapp.schedule.TimeslotState;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import by.katenromanenko.clinicapp.common.error.NotFoundException;


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

    // ------------------------------------------------------------
    // CREATE (вход: AppointmentCreateRequest)
    // ------------------------------------------------------------
    @Override
    public AppointmentDto create(AppointmentCreateRequest request) {

        Appointment entity = new Appointment();

        entity.setAppointmentId(UUID.randomUUID());
        entity.setStatus(AppointmentStatus.SCHEDULED);

        entity.setStartAt(request.getStartAt());
        entity.setDurationMin(request.getDurationMin());
        entity.setDescription(request.getDescription());

        AppUser patient = findUser(request.getPatientId(), "Пациент");
        entity.setPatient(patient);

        AppUser doctor = findUser(request.getDoctorId(), "Доктор");
        entity.setDoctor(doctor);

        if (request.getSlotId() != null) {
            Timeslot slot = findSlot(request.getSlotId());
            validateSlotForBooking(slot);
            entity.setSlot(slot);
        } else {
            entity.setSlot(null);
        }

        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);
        entity.setCanceledAt(null);
        entity.setCancellationReason(null);
        entity.setNotificationSent(false);

        Appointment saved = appointmentRepository.save(entity);

        return appointmentMapper.toDto(saved);
    }

    // ------------------------------------------------------------
    // READ
    // ------------------------------------------------------------
    @Override
    public AppointmentDto getById(UUID id) {
        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Приём не найден: " + id));
        return appointmentMapper.toDto(entity);
    }

    @Override
    public List<AppointmentDto> getAll() {
        List<Appointment> entities = appointmentRepository.findAll();
        return appointmentMapper.toDtoList(entities);
    }

    // ------------------------------------------------------------
    // UPDATE (вход: AppointmentUpdateRequest)
    // ------------------------------------------------------------
    @Override
    public AppointmentDto update(UUID id, AppointmentUpdateRequest request) {

        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Приём не найден: " + id));

        if (request.getStartAt() != null) {
            entity.setStartAt(request.getStartAt());
        }
        if (request.getDurationMin() != null) {
            entity.setDurationMin(request.getDurationMin());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }
        if (request.getCancellationReason() != null) {
            entity.setCancellationReason(request.getCancellationReason());
        }

        if (request.getPatientId() != null) {
            entity.setPatient(findUser(request.getPatientId(), "Пациент"));
        }
        if (request.getDoctorId() != null) {
            entity.setDoctor(findUser(request.getDoctorId(), "Доктор"));
        }

        if (request.getSlotId() != null) {
            Timeslot slot = findSlot(request.getSlotId());
            validateSlotForBooking(slot);
            entity.setSlot(slot);
        }

        entity.setUpdatedAt(LocalDateTime.now());

        // если отменили — ставим время отмены
        if (entity.getStatus() == AppointmentStatus.CANCELED && entity.getCanceledAt() == null) {
            entity.setCanceledAt(LocalDateTime.now());
        }

        Appointment saved = appointmentRepository.save(entity);
        return appointmentMapper.toDto(saved);
    }

    // ------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------
    @Override
    public void delete(UUID id) {

        boolean exists = appointmentRepository.existsById(id);

        if (!exists) {
            throw new NotFoundException("Приём не найден: " + id);
        }

        appointmentRepository.deleteById(id);
    }

    // ------------------------------------------------------------
    // HELPERS
    // ------------------------------------------------------------
    private AppUser findUser(UUID userId, String roleName) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(roleName + " не найден: " + userId));
    }

    private Timeslot findSlot(UUID slotId) {
        return timeslotRepository.findById(slotId)
                .orElseThrow(() -> new NotFoundException("Слот не найден: " + slotId));
    }

    private void validateSlotForBooking(Timeslot slot) {

        if (slot.isBlocked()) {
            throw new IllegalArgumentException("Слот заблокирован.");
        }

        if (slot.getState() != TimeslotState.AVAILABLE) {
            throw new IllegalArgumentException(
                    "Слот недоступен для записи. Текущее состояние: " + slot.getState()
            );
        }
    }
}
