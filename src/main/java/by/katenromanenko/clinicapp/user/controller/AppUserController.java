package by.katenromanenko.clinicapp.user.controller;

import by.katenromanenko.clinicapp.user.AppUserService;
import by.katenromanenko.clinicapp.user.dto.AppUserDto;
import by.katenromanenko.clinicapp.common.error.ErrorResponse;
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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Validated
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
            description = "Список пользователей получен"
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @GetMapping("/{id}")
    public AppUserDto getById(
            @Parameter(description = "UUID пользователя", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
            @PathVariable UUID id
    ) {
        // Если пользователь не найден, сервис кидает IllegalArgumentException,
        // которую перехватывает глобальный обработчик и возвращает 404.
        return appUserService.getById(id);
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
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppUserDto create(
            @Parameter(description = "Данные нового пользователя")
            @Valid @RequestBody AppUserDto dto
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
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PutMapping("/{id}")
    public AppUserDto update(
            @Parameter(description = "UUID пользователя, которого нужно обновить")
            @PathVariable UUID id,

            @Parameter(description = "Новые данные пользователя")
            @Valid @RequestBody AppUserDto dto
    ) {
        return appUserService.update(id, dto);
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/activate")
    public AppUserDto activate(
            @Parameter(description = "UUID пользователя")
            @PathVariable UUID id
    ) {
        return appUserService.activate(id);
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
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь не найден",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}/deactivate")
    public AppUserDto deactivate(
            @Parameter(description = "UUID пользователя")
            @PathVariable UUID id
    ) {
        return appUserService.deactivate(id);
    }
}
