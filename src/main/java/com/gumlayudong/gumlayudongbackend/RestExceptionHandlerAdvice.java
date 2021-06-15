package com.gumlayudong.gumlayudongbackend;

import com.gumlayudong.gumlayudongbackend.exception.CommonException;
import com.gumlayudong.gumlayudongbackend.exception.ExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class RestExceptionHandlerAdvice {
    @ExceptionHandler(CommonException.class)
    public ResponseEntity<ExceptionResponse> commonException(CommonException commonException) {
        String message = commonException.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(new ExceptionResponse(message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> serverException(Exception exception) {
        String message = exception.getMessage();
        log.error(message);
        return ResponseEntity.badRequest().body(new ExceptionResponse(message));
    }
}
