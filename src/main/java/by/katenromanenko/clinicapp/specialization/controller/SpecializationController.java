package by.katenromanenko.clinicapp.specialization.controller;

import by.katenromanenko.clinicapp.specialization.SpecializationService;
import by.katenromanenko.clinicapp.specialization.dto.SpecializationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/specializations")
@RequiredArgsConstructor
public class SpecializationController {

    private final SpecializationService specializationService;

    @GetMapping
    public List<SpecializationDto> getAll() {
        return specializationService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializationDto> getById(@PathVariable UUID id) {
        try {
            SpecializationDto dto = specializationService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SpecializationDto create(@RequestBody SpecializationDto dto) {
        return specializationService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecializationDto> update(@PathVariable UUID id,
                                                    @RequestBody SpecializationDto dto) {
        try {
            SpecializationDto updated = specializationService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        specializationService.delete(id);
    }
}
