package by.katenromanenko.clinicapp.specialization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO специализации врача. Содержит название и описание специальности.")
public class SpecializationDto {

    @Schema(
            description = "Уникальный идентификатор специализации.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID id;

    @Schema(
            description = "Название специализации.",
            example = "Терапевт"
    )
    private String name;

    @Schema(
            description = "Описание специализации или область применения.",
            example = "Специалист, занимающийся диагностикой и лечением широкого круга заболеваний."
    )
    private String description;
}
