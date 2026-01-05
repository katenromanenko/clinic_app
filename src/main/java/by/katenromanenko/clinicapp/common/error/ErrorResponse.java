package by.katenromanenko.clinicapp.common.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private String code;
    private String message;
    private List<String> details;

    private LocalDateTime timestamp;

    private String path;

    private ErrorResponse() {
    }

    public ErrorResponse(
            String code,
            String message,
            List<String> details,
            String path
    ) {
        this.code = code;
        this.message = message;
        this.details = details;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}

