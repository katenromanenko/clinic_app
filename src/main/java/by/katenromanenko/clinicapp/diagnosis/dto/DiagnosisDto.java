package by.katenromanenko.clinicapp.diagnosis.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DiagnosisDto {

    private UUID id;
    private UUID appointmentId;
    private UUID parentRecordId;

    private String text;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

