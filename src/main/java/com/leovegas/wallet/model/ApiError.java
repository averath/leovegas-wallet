package com.leovegas.wallet.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    public static final String ERROR_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @JsonFormat(pattern = ERROR_DATE_FORMAT)
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String error;
    private String message;

    public ApiError(String message, HttpStatus httpStatus) {
        this.message = message;
        status = httpStatus.value();
        error = httpStatus.getReasonPhrase();
    }
}

