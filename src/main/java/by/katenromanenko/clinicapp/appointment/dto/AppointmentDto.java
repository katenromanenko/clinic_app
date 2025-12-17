package by.katenromanenko.clinicapp.appointment.dto;

import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Запись пациента на приём.")
public class AppointmentDto {

    @Schema(
            description = "Идентификатор записи (UUID).",
            example = "2f4cc2a4-8cbd-4a51-9c2d-3cf1fcd51f17",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
            description = "Идентификатор пациента (UUID).",
            example = "7e321423-5a4c-4bd5-9a46-4f8d88d2c3e6"
    )
    @NotNull(message = "patientId обязателен.")
    private UUID patientId;

    @Schema(
            description = "Идентификатор врача (UUID).",
            example = "b59a9e14-6f07-4b83-8ea0-b04df3b9c0e9"
    )
    @NotNull(message = "doctorId обязателен.")
    private UUID doctorId;

    @Schema(
            description = "Идентификатор слота (UUID). Может быть null, если запись создана без привязки к слоту.",
            example = "1a0a2e9b-b276-4b4a-b7a3-084f986a4f8c"
    )
    private UUID slotId;

    @Schema(
            description = "Дата и время начала приёма.",
            example = "2025-12-15T15:00:00"
    )
    @NotNull(message = "Дата и время начала приёма обязательны.")
    @FutureOrPresent(message = "Дата и время начала приёма не могут быть в прошлом.")
    private LocalDateTime startAt;

    @Schema(
            description = "Длительность приёма в минутах.",
            example = "30"
    )
    @NotNull(message = "Длительность приёма обязательна.")
    @Positive(message = "Длительность приёма должна быть больше 0.")
    private Integer durationMin;

    @Schema(
            description = "Текущий статус записи.",
            example = "SCHEDULED"
    )
    @NotNull(message = "Статус записи обязателен.")
    private AppointmentStatus status;

    @Schema(
            description = "Описание / жалобы пациента.",
            example = "Острые боли в спине в течение 3 дней."
    )
    @Size(max = 2000, message = "Описание не может быть длиннее 2000 символов.")
    private String description;

    @Schema(
            description = "Причина отмены приёма.",
            example = "Пациент не пришёл."
    )
    @Size(max = 250, message = "Причина отмены не может быть длиннее 250 символов.")
    private String cancellationReason;

    @Schema(
            description = "Было ли отправлено уведомление пациенту.",
            example = "true",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private boolean notificationSent;

    @Schema(
            description = "Дата создания записи.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Дата последнего обновления записи.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime updatedAt;

    @Schema(
            description = "Дата отмены приёма.",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime canceledAt;

    @AssertTrue(message = "Для отменённого приёма нужно указать причину отмены.")
    public boolean isCancellationReasonValid() {
        if (status == null) {
            return true;
        }

        if (status != AppointmentStatus.CANCELED) {
            return true;
        }

        return cancellationReason != null && !cancellationReason.isBlank();
    }
}
