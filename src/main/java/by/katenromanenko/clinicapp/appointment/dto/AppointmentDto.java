package by.katenromanenko.clinicapp.appointment.dto;

import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Запись пациента на приём (ответ API).")
public class AppointmentDto {

    @Schema(
            description = "Идентификатор записи на приём (UUID).",
            example = "2f4cc2a4-8cbd-4a51-9c2d-3cf1fcd51f17"
    )
    private UUID id;

    @Schema(
            description = "Идентификатор пациента (UUID).",
            example = "7e321423-5a4c-4bd5-9a46-4f8d88d2c3e6"
    )
    private UUID patientId;

    @Schema(
            description = "Идентификатор врача (UUID).",
            example = "b59a9e14-6f07-4b83-8ea0-b04df3b9c0e9"
    )
    private UUID doctorId;

    @Schema(
            description = "Идентификатор слота (UUID), если запись привязана к слоту.",
            example = "1a0a2e9b-b276-4b4a-b7a3-084f986a4f8c"
    )
    private UUID slotId;

    @Schema(
            description = "Дата и время начала приёма.",
            example = "2025-12-15T15:00:00"
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
            description = "Описание / жалобы пациента."
    )
    private String description;

    @Schema(
            description = "Причина отмены приёма, если запись отменена."
    )
    private String cancellationReason;

    @Schema(
            description = "Было ли отправлено уведомление пациенту.",
            example = "false"
    )
    private boolean notificationSent;

    @Schema(
            description = "Дата и время создания записи."
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата и время последнего обновления записи."
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Дата и время отмены приёма."
    )
    private LocalDateTime canceledAt;
}
