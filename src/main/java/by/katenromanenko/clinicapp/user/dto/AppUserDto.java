package by.katenromanenko.clinicapp.user.dto;

import by.katenromanenko.clinicapp.user.SexType;
import by.katenromanenko.clinicapp.user.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Schema(description = "DTO пользователя системы. Содержит личные данные, роль, контактную информацию и состояние учётной записи.")
public class AppUserDto {

    @Schema(
            description = "Уникальный идентификатор пользователя.",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6"
    )
    private UUID id;

    @Schema(
            description = "Логин пользователя для входа в систему.",
            example = "k.romanenko"
    )
    private String login;

    @Schema(
            description = "Хэш пароля пользователя. В API обычно не возвращается.",
            example = "$2a$10$NwqIuC1V2Z6..."
    )
    private String passwordHash;

    @Schema(
            description = "Имя пользователя.",
            example = "Ekaterina"
    )
    private String firstName;

    @Schema(
            description = "Фамилия пользователя.",
            example = "Romanenko"
    )
    private String lastName;

    @Schema(
            description = "Email пользователя.",
            example = "kate@example.com"
    )
    private String email;

    @Schema(
            description = "Телефон пользователя.",
            example = "+375291234567"
    )
    private String phone;

    @Schema(
            description = "Дата рождения пользователя.",
            example = "1994-11-29"
    )
    private LocalDate birthDate;

    @Schema(
            description = "Пол пользователя.",
            example = "FEMALE"
    )
    private SexType sex;

    @Schema(
            description = "Роль пользователя в системе (админ, врач, пациент).",
            example = "DOCTOR"
    )
    private UserRole role;

    @Schema(
            description = "ID специализации врача. Заполняется, если роль = DOCTOR.",
            example = "0c5ae672-6843-4e7b-9eea-fc44d68e7af0"
    )
    private UUID specializationId;

    @Schema(
            description = "Кабинет врача, если пользователь является доктором.",
            example = "302B"
    )
    private String office;

    @Schema(
            description = "Рабочие часы пользователя. Формат произвольный.",
            example = "08:00–17:00"
    )
    private String workHours;

    @Schema(
            description = "Активен ли пользователь. Неактивный пользователь не может войти.",
            example = "true"
    )
    private boolean active;
}
