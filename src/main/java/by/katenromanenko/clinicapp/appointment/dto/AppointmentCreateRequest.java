package by.katenromanenko.clinicapp.appointment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Запрос на создание записи на приём.")
public class AppointmentCreateRequest {

    @Schema(description = "Идентификатор пациента (UUID).")
    @NotNull(message = "patientId обязателен.")
    private UUID patientId;

    @Schema(description = "Идентификатор слота (UUID).")
    @NotNull(message = "slotId обязателен.")
    private UUID slotId;

    @Schema(description = "Дата и время начала приёма.")
    @NotNull(message = "Дата и время начала приёма обязательны.")
    @FutureOrPresent(message = "Дата и время начала приёма не могут быть в прошлом.")
    private LocalDateTime startAt;

    @Schema(description = "Длительность приёма в минутах.")
    @NotNull(message = "Длительность приёма обязательна.")
    @Positive(message = "Длительность приёма должна быть больше 0.")
    private Integer durationMin;

    @Schema(description = "Описание / жалобы пациента.")
    @Size(max = 2000, message = "Описание не может быть длиннее 2000 символов.")
    private String description;
}
