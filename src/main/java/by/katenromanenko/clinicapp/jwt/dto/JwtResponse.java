package by.katenromanenko.clinicapp.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Пара JWT токенов: access для доступа к API и refresh для обновления access токена.")
public class JwtResponse {

    @Schema(
            description = "JWT access token. Передаётся в заголовке Authorization: Bearer <token>.",
            example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcwMDAwMDAwMCwiZXhwIjoxNzAwMDAwOTAwfQ.xxxxx"
    )
    private String accessToken;

    @Schema(
            description = "Refresh token. Хранится на клиенте и используется только для получения нового access token.",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    private String refreshToken;
}

