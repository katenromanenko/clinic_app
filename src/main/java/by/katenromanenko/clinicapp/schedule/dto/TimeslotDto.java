package by.katenromanenko.clinicapp.schedule.dto;

import by.katenromanenko.clinicapp.schedule.TimeslotState;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TimeslotDto {

    private UUID id;

    private UUID doctorId;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private TimeslotState state;

    private boolean blocked;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

