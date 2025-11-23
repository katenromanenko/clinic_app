package by.katenromanenko.clinicapp.appointment.dto;

import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AppointmentDto {

    private UUID id;

    private UUID patientId;
    private UUID doctorId;
    private UUID slotId;

    private LocalDateTime startAt;
    private Integer durationMin;
    private AppointmentStatus status;

    private String description;
    private String cancellationReason;

    private boolean notificationSent;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime canceledAt;
}
