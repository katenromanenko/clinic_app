package by.katenromanenko.clinicapp.common.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        List<String> details = new ArrayList<>();

        for (var error : ex.getBindingResult().getAllErrors()) {
            String message;

            if (error instanceof FieldError fieldError) {
                message = fieldError.getField() + ": " + error.getDefaultMessage();
            } else {
                message = error.getDefaultMessage();
            }

            details.add(message);
        }

        ErrorResponse body = new ErrorResponse(
                "VALIDATION_ERROR",
                "Запрос содержит некорректные данные",
                details
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex) {

        List<String> details = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String message = violation.getPropertyPath() + ": " + violation.getMessage();
            details.add(message);
        }

        ErrorResponse body = new ErrorResponse(
                "VALIDATION_ERROR",
                "Параметры запроса некорректны",
                details
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

        String message = (ex.getMessage() == null || ex.getMessage().isBlank())
                ? "Некорректный запрос"
                : ex.getMessage();

        ErrorResponse body = new ErrorResponse(
                "BAD_REQUEST",
                message,
                Collections.emptyList()
        );

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNoSuchElement(NoSuchElementException ex) {

        String message = (ex.getMessage() == null || ex.getMessage().isBlank())
                ? "Ресурс не найден"
                : ex.getMessage();

        ErrorResponse body = new ErrorResponse(
                "NOT_FOUND",
                message,
                Collections.emptyList()
        );

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex, HttpServletRequest request) {

        log.error("Unexpected error for [{} {}]", request.getMethod(), request.getRequestURI(), ex);

        ErrorResponse body = new ErrorResponse(
                "INTERNAL_ERROR",
                "Внутренняя ошибка сервера. Попробуйте позже.",
                Collections.emptyList()
        );

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
