package by.katenromanenko.clinicapp.jwt.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на вход пользователя по логину и паролю.")
public class SignInRequest {
    @NotBlank(message = "login не должен быть пустым")
    @Schema(description = "Логин пользователя.", example = "admin", requiredMode = Schema.RequiredMode.REQUIRED)
    private String login;

    @NotBlank(message = "password не должен быть пустым")
    @Schema(description = "Пароль пользователя.", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}

