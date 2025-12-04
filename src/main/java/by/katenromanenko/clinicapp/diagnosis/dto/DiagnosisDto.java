package by.katenromanenko.clinicapp.diagnosis.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Schema(description = "Диагностическая запись пациента (диагноз, заметка врача).")
public class DiagnosisDto {

    @Schema(
            description = "Идентификатор записи диагноза (UUID).",
            example = "bb43b1f1-24ff-4b74-b8c3-73944d3df1bb",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(
            description = "Идентификатор приёма, к которому относится диагноз.",
            example = "12d5a3bb-23f4-4d4d-9b0b-687f9fb8bce9"
    )
    @NotNull(message = "appointmentId обязателен.")
    private UUID appointmentId;

    @Schema(
            description = "Идентификатор родительской записи (если это корректировка предыдущей). Может быть null.",
            example = "5e21a256-df91-4c92-9d69-7a18c6b0aa56"
    )
    private UUID parentRecordId;

    @Schema(
            description = "Текст диагноза или записи врача.",
            example = "Пациент жалуется на боли в пояснице последние 4 дня."
    )
    @NotBlank(message = "Текст диагноза обязателен.")
    @Size(max = 5000, message = "Текст диагноза не может превышать 5000 символов.")
    private String text;

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
}
