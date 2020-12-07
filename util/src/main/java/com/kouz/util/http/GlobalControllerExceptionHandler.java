package com.kouz.util.http;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kouz.util.exceptions.InvalidInputException;
import com.kouz.util.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public HttpErrorInfo handleNotFoundException(ServerHttpRequest request, Exception exception) {

        return createHttpErrorInfo(NOT_FOUND, request, exception);
    }


    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody HttpErrorInfo handleInvalidInputException(ServerHttpRequest request, Exception ex) {

        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex);
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, ServerHttpRequest request, Exception ex) {
        final String path = request.getPath().pathWithinApplication().value();
        final String message = ex.getMessage();

        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return HttpErrorInfo.now(path, httpStatus, message);
    }
}
