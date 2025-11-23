package by.katenromanenko.clinicapp.specialization.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class SpecializationDto {

    private UUID id;
    private String name;
    private String description;
}
