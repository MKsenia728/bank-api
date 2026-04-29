package com.oksanamolchanova.bank.api.controller.handler;


import com.oksanamolchanova.bank.api.dto.ErrorExtensionDto;
import com.oksanamolchanova.bank.api.dto.ErrorResponseDto;
import com.oksanamolchanova.bank.api.service.exceptions.DataAlreadyExistException;
import com.oksanamolchanova.bank.api.service.exceptions.DataNotFoundException;
import com.oksanamolchanova.bank.api.service.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestControllerAdvice
@Slf4j
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(ConstraintViolationException.class)
    @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponseDto.class))
    })
    public ResponseEntity<ErrorResponseDto> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ErrorExtensionDto> extensions = exception.getConstraintViolations()
                .stream()
                .map(violation -> new ErrorExtensionDto(violation.getMessage(), ErrorMessage.INVALID_PATH_VARIABLE))
                .collect(Collectors.toList());
        return new ResponseEntity<>(new ErrorResponseDto(ErrorMessage.VALIDATION_FAILED, extensions), BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataNotFoundException.class)
//    @ApiResponse(responseCode = "404", description = "Data not found", content = {
//            @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = ErrorExtensionDto.class))
//    })
    public ResponseEntity<ErrorExtensionDto> handleNotFoundException(DataNotFoundException e) {
        log.error("", e);
        ErrorExtensionDto error = new ErrorExtensionDto(Integer.toString(HttpURLConnection.HTTP_NOT_FOUND), e.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_FOUND).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(DataAlreadyExistException.class)
//    @ApiResponse(responseCode = "406", description = "Data already exists", content = {
//            @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = ErrorExtensionDto.class))
//    })
    public ResponseEntity<ErrorExtensionDto> handleDataAlreadyExistException(DataAlreadyExistException e) {
        log.error("", e);
        ErrorExtensionDto error = new ErrorExtensionDto(Integer.toString(HttpURLConnection.HTTP_NOT_ACCEPTABLE), e.getMessage());
        return ResponseEntity.status(HttpURLConnection.HTTP_NOT_ACCEPTABLE).body(error);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
//    @ApiResponse(responseCode = "400", description = "It is bed request", content = {
//            @Content(mediaType = "application/json",
//                    schema = @Schema(implementation = ErrorMessage.class))
//    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();

        for (FieldError error : result.getFieldErrors()) {
            errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
        }
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<ErrorExtensionDto> handleUncaughtException(Throwable e) {
        String message = "This error is not provided";
        log.error(message, e);
        ErrorExtensionDto error = new ErrorExtensionDto(Integer.toString(HttpURLConnection.HTTP_INTERNAL_ERROR), message);
        return ResponseEntity.status(HttpURLConnection.HTTP_INTERNAL_ERROR).body(error);
    }
}
