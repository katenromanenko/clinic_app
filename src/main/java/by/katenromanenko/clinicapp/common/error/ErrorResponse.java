package by.katenromanenko.clinicapp.common.error;

import lombok.Data;

import java.util.List;

@Data
public class ErrorResponse {

    private String code;

    private String message;

    private List<String> details;

    private ErrorResponse() {

    }
    public ErrorResponse(String code, String message, List<String> details) {
        this.code = code;
        this.message = message;
        this.details = details;
    }
}
