package by.katenromanenko.clinicapp.appointment.dto;

import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(
        description = "DTO для записи на приём. Содержит полную информацию о назначении визита, его статусе и временных метках."
)
public class AppointmentDto {

    @Schema(
            description = "Идентификатор записи на приём.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID id;

    @Schema(
            description = "ID пациента, который записан на приём.",
            example = "d1fcdd28-35c0-4e8b-843b-bf28f4d534e3"
    )
    private UUID patientId;

    @Schema(
            description = "ID врача, к которому осуществляется запись.",
            example = "b4c44a25-9024-4b0f-a516-6c6196b12fd2"
    )
    private UUID doctorId;

    @Schema(
            description = "ID таймслота, выбранного для приёма.",
            example = "7e6b6a76-5826-4529-afae-b2b46bb9ea93"
    )
    private UUID slotId;

    @Schema(
            description = "Дата и время начала приёма.",
            example = "2025-12-12T14:30:00"
    )
    private LocalDateTime startAt;

    @Schema(
            description = "Длительность приёма в минутах.",
            example = "30"
    )
    private Integer durationMin;

    @Schema(
            description = "Текущий статус записи.",
            example = "SCHEDULED"
    )
    private AppointmentStatus status;

    @Schema(
            description = "Описание или комментарий пациента/врача.",
            example = "Первичный приём. Жалобы на боль в горле."
    )
    private String description;

    @Schema(
            description = "Причина отмены записи, если она была отменена.",
            example = "Пациент заболел"
    )
    private String cancellationReason;

    @Schema(
            description = "Отправлено ли уведомление пациенту.",
            example = "true"
    )
    private boolean notificationSent;

    @Schema(
            description = "Дата и время создания записи.",
            example = "2025-12-10T10:15:00"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата и время последнего обновления записи.",
            example = "2025-12-11T11:20:00"
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Дата и время отмены записи, если запись была отменена.",
            example = "2025-12-11T09:00:00"
    )
    private LocalDateTime canceledAt;
}
