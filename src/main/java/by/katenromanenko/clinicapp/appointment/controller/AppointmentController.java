package by.katenromanenko.clinicapp.appointment.controller;

import by.katenromanenko.clinicapp.appointment.AppointmentService;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
@Tag(
        name = "Appointments",
        description = "CRUD-операции с записями на приём к врачу."
)
public class AppointmentController {

    private final AppointmentService appointmentService;

    // ------------------------------------------------------------
    // GET /api/appointments
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить список всех записей",
            description = "Возвращает полный список всех записей на приём без фильтрации."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список успешно получен",
            content = @Content(schema = @Schema(implementation = AppointmentDto.class))
    )
    @GetMapping
    public List<AppointmentDto> getAll() {
        return appointmentService.getAll();
    }

    // ------------------------------------------------------------
    // GET /api/appointments/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить запись по ID",
            description = "Возвращает одну запись на приём по UUID. Если запись не найдена — 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запись найдена",
                    content = @Content(schema = @Schema(implementation = AppointmentDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getById(
            @Parameter(
                    description = "UUID записи на приём",
                    example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
            )
            @PathVariable UUID id
    ) {
        try {
            AppointmentDto dto = appointmentService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // POST /api/appointments
    // ------------------------------------------------------------
    @Operation(
            summary = "Создать новую запись",
            description = "Создаёт новую запись на приём на основе данных DTO."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Запись успешно создана",
                    content = @Content(schema = @Schema(implementation = AppointmentDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Переданы неверные данные",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto create(
            @Parameter(description = "Данные новой записи на приём")
            @RequestBody AppointmentDto dto
    ) {
        return appointmentService.create(dto);
    }

    // ------------------------------------------------------------
    // PUT /api/appointments/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Обновить существующую запись",
            description = "Полностью обновляет запись на приём по указанному UUID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Запись обновлена",
                    content = @Content(schema = @Schema(implementation = AppointmentDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Запись не найдена",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> update(
            @Parameter(description = "UUID записи на приём")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные записи")
            @RequestBody AppointmentDto dto
    ) {
        try {
            AppointmentDto updated = appointmentService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE /api/appointments/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Удалить запись",
            description = "Удаляет запись по UUID. Если записи нет — всё равно 204 (по REST-конвенции)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Запись удалена"
            )
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "UUID записи на приём")
            @PathVariable UUID id
    ) {
        appointmentService.delete(id);
    }
}
