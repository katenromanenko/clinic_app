package by.katenromanenko.clinicapp.schedule.dto;

import by.katenromanenko.clinicapp.schedule.TimeslotState;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Модель данных временного слота врача")
public class TimeslotDto {

    @Schema(
            description = "Уникальный идентификатор таймслота",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID id;

    @Schema(
            description = "Идентификатор врача, которому принадлежит таймслот",
            example = "b2e1cbd4-d3fc-4e48-8e18-9fda86c4f23d"
    )
    private UUID doctorId;

    @Schema(
            description = "Дата и время начала таймслота",
            example = "2025-12-03T10:00:00"
    )
    private LocalDateTime startTime;

    @Schema(
            description = "Дата и время окончания таймслота",
            example = "2025-12-03T10:30:00"
    )
    private LocalDateTime endTime;

    @Schema(
            description = "Текущий статус таймслота (AVAILABLE, BOOKED, CANCELED и т.д.)"
    )
    private TimeslotState state;

    @Schema(
            description = "Заблокирован ли слот системой или администратором",
            example = "false"
    )
    private boolean blocked;

    @Schema(
            description = "Время создания таймслота",
            example = "2025-12-01T09:12:45"
    )
    private LocalDateTime createdAt;

    @Schema(
            description = "Время последнего обновления таймслота",
            example = "2025-12-01T10:45:30"
    )
    private LocalDateTime updatedAt;
}
