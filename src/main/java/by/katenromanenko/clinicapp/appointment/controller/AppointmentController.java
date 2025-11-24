package by.katenromanenko.clinicapp.appointment.controller;

import by.katenromanenko.clinicapp.appointment.AppointmentService;
import by.katenromanenko.clinicapp.appointment.dto.AppointmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDto> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getById(@PathVariable UUID id) {
        try {
            AppointmentDto dto = appointmentService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentDto create(@RequestBody AppointmentDto dto) {
        return appointmentService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDto> update(@PathVariable UUID id,
                                                 @RequestBody AppointmentDto dto) {
        try {
            AppointmentDto updated = appointmentService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        appointmentService.delete(id);
    }
}
