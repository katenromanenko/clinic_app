package by.katenromanenko.clinicapp.user.controller;

import by.katenromanenko.clinicapp.user.AppUserService;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
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
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(
        name = "Users",
        description = "CRUD-операции над пользователями системы: создание, обновление, просмотр, активация и деактивация."
)
public class AppUserController {

    private final AppUserService appUserService;

    // ------------------------------------------------------------
    // GET /api/users
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить список всех пользователей",
            description = "Возвращает полную выборку пользователей системы без фильтрации."
    )
    @ApiResponse(
            responseCode = "200",
            description = "Список найден"
    )
    @GetMapping
    public List<AppUserDto> getAll() {
        return appUserService.getAll();
    }

    // ------------------------------------------------------------
    // GET /api/users/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Получить пользователя по ID",
            description = "Возвращает данные пользователя по его уникальному идентификатору UUID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getById(
            @Parameter(description = "UUID пользователя", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        try {
            AppUserDto dto = appUserService.getById(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // POST /api/users
    // ------------------------------------------------------------
    @Operation(
            summary = "Создать нового пользователя",
            description = "Создаёт нового пользователя и возвращает созданный объект."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Пользователь успешно создан"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные",
                    content = @Content(schema = @Schema(hidden = true))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto create(
            @Parameter(description = "Данные нового пользователя")
            @RequestBody AppUserDto dto
    ) {
        return appUserService.create(dto);
    }

    // ------------------------------------------------------------
    // PUT /api/users/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Обновить пользователя",
            description = "Полностью обновляет данные пользователя с указанным ID."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppUserDto> update(
            @Parameter(description = "UUID пользователя, которого нужно обновить")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные пользователя")
            @RequestBody AppUserDto dto
    ) {
        try {
            AppUserDto updated = appUserService.update(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // DELETE /api/users/{id}
    // ------------------------------------------------------------
    @Operation(
            summary = "Удалить пользователя",
            description = "Удаляет пользователя по его ID и возвращает статус без тела ответа."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "UUID пользователя, которого нужно удалить")
            @PathVariable UUID id
    ) {
        appUserService.delete(id);
    }

    // ------------------------------------------------------------
    // PATCH /api/users/{id}/activate
    // ------------------------------------------------------------
    @Operation(
            summary = "Активировать пользователя",
            description = "Переводит пользователя в состояние active=true."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь активирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PatchMapping("/{id}/activate")
    public ResponseEntity<AppUserDto> activate(
            @Parameter(description = "UUID пользователя")
            @PathVariable UUID id
    ) {
        try {
            AppUserDto dto = appUserService.activate(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    // ------------------------------------------------------------
    // PATCH /api/users/{id}/deactivate
    // ------------------------------------------------------------
    @Operation(
            summary = "Деактивировать пользователя",
            description = "Переводит пользователя в состояние active=false."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь деактивирован"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<AppUserDto> deactivate(
            @Parameter(description = "UUID пользователя")
            @PathVariable UUID id
    ) {
        try {
            AppUserDto dto = appUserService.deactivate(id);
            return ResponseEntity.ok(dto);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
