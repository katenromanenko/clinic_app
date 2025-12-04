package by.katenromanenko.clinicapp.user.dto;

import by.katenromanenko.clinicapp.user.SexType;
import by.katenromanenko.clinicapp.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "Пользователь системы (пациент или врач).")
public class AppUserDto {

    @Schema(
            description = "Идентификатор пользователя (UUID).",
            example = "d290f1ee-6c54-4b01-90e6-d701748f0851",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private UUID id;

    @Schema(description = "Уникальный логин пользователя.", example = "j.doe")
    @NotBlank(message = "Логин обязателен.")
    @Size(max = 100, message = "Логин не может быть длиннее 100 символов.")
    private String login;

    @Schema(
            description = "Пароль.",
            example = "Qwerty123!"
    )
    @NotBlank(message = "Пароль обязателен.")
    @Size(min = 8, max = 255, message = "Пароль должен быть от 8 до 255 символов.")
    private String passwordHash;

    @Schema(description = "Имя пользователя.", example = "Иван")
    @NotBlank(message = "Имя обязательно.")
    @Size(max = 100, message = "Имя не может быть длиннее 100 символов.")
    private String firstName;

    @Schema(description = "Фамилия пользователя.", example = "Иванов")
    @NotBlank(message = "Фамилия обязательна.")
    @Size(max = 100, message = "Фамилия не может быть длиннее 100 символов.")
    private String lastName;

    @Schema(description = "Email пользователя.", example = "ivanov@example.com")
    @NotBlank(message = "Email обязателен.")
    @Email(message = "Некорректный формат email.")
    @Size(max = 200, message = "Email не может быть длиннее 200 символов.")
    private String email;

    @Schema(description = "Номер телефона.", example = "+375291234567")
    @Size(max = 30, message = "Телефон не может быть длиннее 30 символов.")
    private String phone;

    @Schema(description = "Дата рождения (для пациента).", example = "1990-05-20")
    @Past(message = "Дата рождения должна быть в прошлом.")
    private LocalDate birthDate;

    @Schema(description = "Пол пользователя.", example = "FEMALE")
    private SexType sex;

    @Schema(description = "Роль пользователя.", example = "DOCTOR")
    @NotNull(message = "Роль пользователя обязательна.")
    private UserRole role;

    @Schema(description = "Идентификатор специализации врача.", example = "b59a9e14-6f07-4b83-8ea0-b04df3b9c0e9")
    private UUID specializationId;

    @Schema(description = "Номер кабинета врача.", example = "312")
    @Size(max = 50, message = "Кабинет не может быть длиннее 50 символов.")
    private String office;

    @Schema(description = "Рабочие часы врача.", example = "Пн–Пт 9:00–18:00")
    @Size(max = 100, message = "Описание рабочих часов не может быть длиннее 100 символов.")
    private String workHours;

    @Schema(description = "Признак активности пользователя.", example = "true")
    private boolean active;

    @AssertTrue(message = "Для роли DOCTOR нужно указать specializationId.")
    public boolean isDoctorHasSpecialization() {
        if (role == null) {
            return true;
        }
        if (role != UserRole.DOCTOR) {
            return true;
        }
        return specializationId != null;
    }
}
