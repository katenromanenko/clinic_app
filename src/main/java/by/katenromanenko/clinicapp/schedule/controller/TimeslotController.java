package by.katenromanenko.clinicapp.schedule.controller;

import by.katenromanenko.clinicapp.schedule.TimeslotService;
import by.katenromanenko.clinicapp.schedule.dto.TimeslotDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/timeslots")
@RequiredArgsConstructor
public class TimeslotController {

    private final TimeslotService timeslotService;

    @GetMapping
    public List<TimeslotDto> getAll() {
        return timeslotService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TimeslotDto> getById(@PathVariable UUID id) {
        try {
            TimeslotDto dto = timeslotService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimeslotDto create(@RequestBody TimeslotDto dto) {
        return timeslotService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TimeslotDto> update(@PathVariable UUID id,
                                              @RequestBody TimeslotDto dto) {
        try {
            TimeslotDto updated = timeslotService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        timeslotService.delete(id);
    }
}

