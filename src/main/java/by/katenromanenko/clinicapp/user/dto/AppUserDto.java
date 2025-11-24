package by.katenromanenko.clinicapp.user.dto;

import by.katenromanenko.clinicapp.user.SexType;
import by.katenromanenko.clinicapp.user.UserRole;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AppUserDto {

    private UUID id;
    private String login;

    private String passwordHash;

    private String firstName;
    private String lastName;

    private String email;
    private String phone;

    private LocalDate birthDate;

    private SexType sex;

    private UserRole role;

    private UUID specializationId;

    private String office;
    private String workHours;

    private boolean active;
}
