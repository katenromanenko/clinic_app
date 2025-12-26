package by.katenromanenko.clinicapp.jwt;

import by.katenromanenko.clinicapp.common.error.ErrorResponse;
import by.katenromanenko.clinicapp.jwt.dto.JwtResponse;
import by.katenromanenko.clinicapp.jwt.dto.RefreshRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Authentication",
        description = "Аутентификация пользователей и обновление JWT-токенов"
)
public class TokenController {

    private final RefreshTokenService refreshTokenService;

    @Operation(
            summary = "Обновить access token",
            description = "Принимает refreshToken и возвращает новую пару токенов. Refresh token ротируется (старый становится недействителен)."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Токены успешно обновлены.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtResponse.class),
                            examples = @ExampleObject(
                                    name = "Success",
                                    value = """
                                            {
                                              "accessToken": "eyJhbGciOiJIUzI1NiJ9...newAccess...",
                                              "refreshToken": "2f1c4c2a-8cbd-4a51-9c2d-3cf1fcd51f17"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Пустой refreshToken или неверный формат запроса.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Validation error",
                                    value = """
                                            {
                                              "code": "VALIDATION_ERROR",
                                              "message": "Некорректные данные запроса",
                                              "details": ["refreshToken не должен быть пустым"]
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Refresh token невалиден или истёк.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "Invalid refresh token",
                                    value = """
                                            {
                                              "code": "UNAUTHORIZED",
                                              "message": "Refresh token не валиден или истек",
                                              "details": []
                                            }
                                            """
                            )
                    )
            )
    })
    @PostMapping("/refresh")
    public JwtResponse refresh(@Valid @RequestBody RefreshRequest request) {
        return refreshTokenService.refresh(request.getRefreshToken());
    }
}

