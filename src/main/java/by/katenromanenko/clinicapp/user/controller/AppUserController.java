package by.katenromanenko.clinicapp.user.controller;

import by.katenromanenko.clinicapp.user.AppUserService;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    @GetMapping
    public List<AppUserDto> getAll() {
        return appUserService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getById(@PathVariable UUID id) {
        try {
            AppUserDto dto = appUserService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto create(@RequestBody AppUserDto dto) {
        return appUserService.create(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppUserDto> update(@PathVariable UUID id,
                                             @RequestBody AppUserDto dto) {
        try {
            AppUserDto updated = appUserService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        appUserService.delete(id);
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<AppUserDto> activate(@PathVariable UUID id) {
        try {
            AppUserDto dto = appUserService.activate(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AppUserDto> deactivate(@PathVariable UUID id) {
        try {
            AppUserDto dto = appUserService.deactivate(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
