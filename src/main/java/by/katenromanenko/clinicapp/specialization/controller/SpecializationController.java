package by.katenromanenko.clinicapp.specialization.controller;

import by.katenromanenko.clinicapp.specialization.SpecializationService;
import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
@Tag(
        name = "Specializations",
        description = "CRUD-операции со специализациями врачей."
)
public class SpecializationController {

    private final SpecializationService specializationService;

    // ------------------------------------------------------------
    // GET /api/specializations
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить список всех специализаций",
            description = "Возвращает список всех доступных врачебных специализаций."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список успешно получен",
            content = @Content(schema = @Schema(implementation = SpecializationDto.class))
    )
    @GetMapping
    public List<SpecializationDto> getAll() {
        return specializationService.getAll();
    }

    // ------------------------------------------------------------
    // GET /api/specializations/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить специализацию по ID",
            description = "Возвращает одну специализацию по её UUID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Специализация найдена",
                    content = @Content(schema = @Schema(implementation = SpecializationDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Специализация не найдена",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<SpecializationDto> getById(
            @Parameter(
                    description = "UUID специализации",
                    example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            )
            @PathVariable UUID id
    ) {
        SpecializationDto dto = specializationService.getById(id);
        return ResponseEntity.ok(dto);
    }

    // ------------------------------------------------------------
    // POST /api/specializations
    // ------------------------------------------------------------
    @Operation(
            summary = "Создать новую специализацию",
            description = "Добавляет новую врачебную специализацию."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Специализация успешно создана",
                    content = @Content(schema = @Schema(implementation = SpecializationDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecializationDto create(
            @Parameter(description = "Данные новой специализации")
            @Valid @RequestBody SpecializationDto dto
    ) {
        return specializationService.create(dto);
    }

    // ------------------------------------------------------------
    // PUT /api/specializations/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Обновить специализацию",
            description = "Полностью заменяет данные существующей специализации по UUID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Специализация обновлена",
                    content = @Content(schema = @Schema(implementation = SpecializationDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Неверные данные",
                    content = @Content(schema = @Schema(hidden = true))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Специализация не найдена",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<SpecializationDto> update(
            @Parameter(description = "UUID специализации для обновления")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные специализации")
            @Valid @RequestBody SpecializationDto dto
    ) {
        SpecializationDto updated = specializationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // ------------------------------------------------------------
    // DELETE /api/specializations/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Удалить специализацию",
            description = "Удаляет специализацию по UUID. Если её нет — возвращается 204."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Специализация удалена"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "UUID специализации")
            @PathVariable UUID id
    ) {
        specializationService.delete(id);
    }
}
