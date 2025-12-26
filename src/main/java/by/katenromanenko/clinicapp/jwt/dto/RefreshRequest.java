package by.katenromanenko.clinicapp.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на обновление access token с помощью refresh token.")
public class RefreshRequest {

    @NotBlank(message = "refreshToken не должен быть пустым")
    @Schema(
            description = "Refresh token, ранее полученный при входе или при обновлении токена.",
            example = "550e8400-e29b-41d4-a716-446655440000",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String refreshToken;
}
