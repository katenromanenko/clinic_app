package by.katenromanenko.clinicapp.schedule.controller;

import by.katenromanenko.clinicapp.schedule.TimeslotService;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
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
@RequestMapping("/api/timeslots")
@RequiredArgsConstructor
@Tag(
        name = "Timeslots",
        description = "Управление временными слотами для расписания врачей."
)
public class TimeslotController {

    private final TimeslotService timeslotService;

    // ------------------------------------------------------------
    // GET /api/timeslots
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить список всех таймслотов",
            description = "Возвращает список всех временных интервалов, доступных для записи."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список успешно получен",
            content = @Content(schema = @Schema(implementation = TimeslotDto.class))
    )
    @GetMapping
    public List<TimeslotDto> getAll() {
        return timeslotService.getAll();
    }

    // ------------------------------------------------------------
    // GET /api/timeslots/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить таймслот по ID",
            description = "Возвращает данные о конкретном таймслоте. Если он не найден — 404."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Таймслот найден",
                    content = @Content(schema = @Schema(implementation = TimeslotDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Таймслот не найден",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<TimeslotDto> getById(
            @Parameter(description = "UUID таймслота", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        try {
            TimeslotDto dto = timeslotService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // POST /api/timeslots
    // ------------------------------------------------------------
    @Operation(
            summary = "Создать новый таймслот",
            description = "Добавляет новый временной интервал для врача."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Таймслот успешно создан",
                    content = @Content(schema = @Schema(implementation = TimeslotDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Ошибка в данных запроса",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeslotDto create(
            @Parameter(description = "Данные нового таймслота")
            @RequestBody TimeslotDto dto
    ) {
        return timeslotService.create(dto);
    }

    // ------------------------------------------------------------
    // PUT /api/timeslots/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Обновить таймслот",
            description = "Полностью заменяет данные существующего таймслота по UUID."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Таймслот обновлён",
                    content = @Content(schema = @Schema(implementation = TimeslotDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Таймслот не найден",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<TimeslotDto> update(
            @Parameter(description = "UUID обновляемого таймслота")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные таймслота")
            @RequestBody TimeslotDto dto
    ) {
        try {
            TimeslotDto updated = timeslotService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE /api/timeslots/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Удалить таймслот",
            description = "Удаляет таймслот по UUID. Если он не найден — возвращается 204."
    )
    @ApiResponse(
            responseCode = "204",
            description = "Таймслот удалён"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "UUID таймслота")
            @PathVariable UUID id
    ) {
        timeslotService.delete(id);
    }
}
