package by.katenromanenko.clinicapp.appointment;

import by.katenromanenko.clinicapp.appointment.dto.AppointmentCreateRequest;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentUpdateRequest;
import by.katenromanenko.clinicapp.appointment.mapper.AppointmentMapper;
import by.katenromanenko.clinicapp.common.error.NotFoundException;
import by.katenromanenko.clinicapp.schedule.Timeslot;
import by.katenromanenko.clinicapp.schedule.TimeslotRepository;
import by.katenromanenko.clinicapp.schedule.TimeslotState;
import by.katenromanenko.clinicapp.user.AppUser;
import by.katenromanenko.clinicapp.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private static final int SLOT_DURATION_MIN = 30;

    private final AppointmentRepository appointmentRepository;
    private final AppUserRepository appUserRepository;
    private final TimeslotRepository timeslotRepository;
    private final AppointmentMapper appointmentMapper;

    // ------------------------------------------------------------
    // CREATE
    // ------------------------------------------------------------
    @Override
    @Transactional
    public AppointmentDto create(AppointmentCreateRequest request) {

        AppUser patient = findUser(request.getPatientId(), "Пациент");

        Timeslot slot = findSlot(request.getSlotId());
        validateSlotForBooking(slot);

        AppUser doctor = slot.getDoctor();

        if (!request.getStartAt().equals(slot.getStartTime())) {
            throw new IllegalArgumentException("startAt должен совпадать со временем начала слота.");
        }

        if (!Integer.valueOf(SLOT_DURATION_MIN).equals(request.getDurationMin())) {
            throw new IllegalArgumentException("durationMin должен быть равен 30 минутам.");
        }

        Appointment entity = new Appointment();
        entity.setAppointmentId(UUID.randomUUID());
        entity.setPatient(patient);
        entity.setDoctor(doctor);
        entity.setSlot(slot);

        entity.setStartAt(slot.getStartTime());
        entity.setDurationMin(SLOT_DURATION_MIN);
        entity.setDescription(request.getDescription());

        entity.setStatus(AppointmentStatus.SCHEDULED);
        entity.setCancellationReason(null);
        entity.setNotificationSent(false);
        entity.setCreatedAt(LocalDateTime.now());
        entity.setUpdatedAt(null);
        entity.setCanceledAt(null);

        Appointment saved = appointmentRepository.save(entity);

        slot.setState(TimeslotState.BOOKED);
        slot.setUpdatedAt(LocalDateTime.now());
        timeslotRepository.save(slot);

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
        return appointmentMapper.toDtoList(appointmentRepository.findAll());
    }

    // ------------------------------------------------------------
    // UPDATE
    // ------------------------------------------------------------
    @Override
    @Transactional
    public AppointmentDto update(UUID id, AppointmentUpdateRequest request) {

        Appointment entity = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Приём не найден: " + id));

        if (request.getSlotId() != null) {
            Timeslot newSlot = findSlot(request.getSlotId());
            validateSlotForBooking(newSlot);

            UUID currentDoctorId = entity.getDoctor().getUserId();
            if (!newSlot.getDoctor().getUserId().equals(currentDoctorId)) {
                throw new IllegalArgumentException("Нельзя перенести приём на слот другого врача.");
            }

            Timeslot oldSlot = entity.getSlot();
            if (oldSlot != null) {
                oldSlot.setState(TimeslotState.AVAILABLE);
                oldSlot.setUpdatedAt(LocalDateTime.now());
                timeslotRepository.save(oldSlot);
            }

            newSlot.setState(TimeslotState.BOOKED);
            newSlot.setUpdatedAt(LocalDateTime.now());
            timeslotRepository.save(newSlot);

            entity.setSlot(newSlot);
            entity.setStartAt(newSlot.getStartTime());
            entity.setDurationMin(SLOT_DURATION_MIN);
        }

        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }

        if (request.getStatus() != null) {

            AppointmentStatus newStatus = request.getStatus();

            if (newStatus == AppointmentStatus.CANCELED) {

                if (request.getCancellationReason() == null || request.getCancellationReason().isBlank()) {
                    throw new IllegalArgumentException("Для отмены нужна cancellationReason.");
                }

                if (entity.getStatus() == AppointmentStatus.CANCELED) {
                    throw new IllegalArgumentException("Приём уже отменён.");
                }

                entity.setStatus(AppointmentStatus.CANCELED);
                entity.setCancellationReason(request.getCancellationReason());
                entity.setCanceledAt(LocalDateTime.now());

                // освобождаем слот
                Timeslot slot = entity.getSlot();
                if (slot != null) {
                    slot.setState(TimeslotState.AVAILABLE);
                    slot.setUpdatedAt(LocalDateTime.now());
                    timeslotRepository.save(slot);
                }

            } else {
                entity.setStatus(newStatus);
            }
        }

        entity.setUpdatedAt(LocalDateTime.now());

        Appointment saved = appointmentRepository.save(entity);
        return appointmentMapper.toDto(saved);
    }
    // ------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------
    @Override
    public void delete(UUID id) {
        if (!appointmentRepository.existsById(id)) {
            throw new NotFoundException("Приём не найден: " + id);
        }
        appointmentRepository.deleteById(id);
    }

    // ------------------------------------------------------------
    // HELPERS
    // ------------------------------------------------------------
    private AppUser findUser(UUID userId, String title) {
        return appUserRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(title + " не найден: " + userId));
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
            throw new IllegalArgumentException("Слот недоступен. Текущее состояние: " + slot.getState());
        }
    }
}
