package by.katenromanenko.clinicapp.specialization.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "Медицинская специализация врача.")
public class SpecializationDto {

    @Schema(
            description = "Идентификатор специализации (UUID).",
            example = "f2cf5b1e-0e4a-4fd8-b1fa-7f0d4f52e5a1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(description = "Название специализации.", example = "Терапевт")
    @NotBlank(message = "Название специализации обязательно.")
    @Size(max = 120, message = "Название специализации не может быть длиннее 120 символов.")
    private String name;

    @Schema(description = "Подробное описание специализации.", example = "Врач общей практики.")
    @Size(max = 2000, message = "Описание не может быть длиннее 2000 символов.")
    private String description;
}
