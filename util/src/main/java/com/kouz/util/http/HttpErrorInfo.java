package com.kouz.util.http;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class HttpErrorInfo {
    private final ZonedDateTime timestamp;
    private final String path;
    private final HttpStatus httpStatus;
    private final String message;

    public static HttpErrorInfo now(String path, HttpStatus httpStatus, String message) {
        return new HttpErrorInfo(ZonedDateTime.now(), path, httpStatus, message);
    }
}
