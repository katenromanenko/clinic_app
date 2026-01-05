package by.katenromanenko.clinicapp.appointment.dto;

import by.katenromanenko.clinicapp.appointment.AppointmentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Запрос на обновление записи на приём (частичное).")
public class AppointmentUpdateRequest {

    @Schema(description = "Идентификатор пациента (UUID).")
    private UUID patientId;

    @Schema(description = "Идентификатор слота (UUID).")
    private UUID slotId;

    @Schema(description = "Дата и время начала приёма.")
    @FutureOrPresent(message = "Дата и время начала приёма не могут быть в прошлом.")
    private LocalDateTime startAt;

    @Schema(description = "Длительность приёма в минутах.")
    @Positive(message = "Длительность приёма должна быть больше 0.")
    private Integer durationMin;

    @Schema(description = "Новый статус записи.")
    private AppointmentStatus status;

    @Schema(description = "Описание / жалобы пациента.")
    @Size(max = 2000, message = "Описание не может быть длиннее 2000 символов.")
    private String description;

    @Schema(description = "Причина отмены приёма (нужна если статус CANCELED).")
    @Size(max = 250, message = "Причина отмены не может быть длиннее 250 символов.")
    private String cancellationReason;

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

