package by.katenromanenko.clinicapp.schedule.dto;

import by.katenromanenko.clinicapp.schedule.TimeslotState;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Временной слот приёма врача.")
public class TimeslotDto {

    @Schema(
            description = "Идентификатор слота (UUID).",
            example = "1a0a2e9b-b276-4b4a-b7a3-084f986a4f8c",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(description = "Идентификатор врача (UUID).", example = "b59a9e14-6f07-4b83-8ea0-b04df3b9c0e9")
    @NotNull(message = "doctorId обязателен.")
    private UUID doctorId;

    @Schema(description = "Время начала слота.", example = "2025-12-10T10:00:00")
    @NotNull(message = "Время начала слота обязательно.")
    private LocalDateTime startTime;

    @Schema(description = "Время окончания слота.", example = "2025-12-10T10:30:00")
    @NotNull(message = "Время окончания слота обязательно.")
    private LocalDateTime endTime;

    @Schema(description = "Состояние слота.", example = "FREE")
    @NotNull(message = "Состояние слота обязательно.")
    private TimeslotState state;

    @Schema(description = "Заблокирован ли слот вручную.", example = "false")
    private boolean blocked;

    @Schema(description = "Дата создания записи.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "Дата последнего обновления записи.", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @AssertTrue(message = "Время окончания слота должно быть позже времени начала.")
    public boolean isEndAfterStart() {
        if (startTime == null || endTime == null) {
            return true;
        }
        return endTime.isAfter(startTime);
    }
}
