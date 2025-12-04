package by.katenromanenko.clinicapp.schedule.controller;

import by.katenromanenko.clinicapp.schedule.TimeslotService;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @GetMapping
    @Operation(summary = "Получить список всех таймслотов")
    public List<TimeslotDto> getAll() {
        return timeslotService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить таймслот по ID")
    public TimeslotDto getById(@PathVariable UUID id) {
        return timeslotService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать новый таймслот")
    public TimeslotDto create(@Valid @RequestBody TimeslotDto dto) {
        return timeslotService.create(dto);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить таймслот")
    public TimeslotDto update(
            @PathVariable UUID id,
            @Valid @RequestBody TimeslotDto dto
    ) {
        return timeslotService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить таймслот")
    public void delete(@PathVariable UUID id) {
        timeslotService.delete(id);
    }
}
