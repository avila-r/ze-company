package com.avila.zecompany.exception.advice.handler;
import com.avila.zecompany.exception.advice.UnmappedEndpointException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AdviceExceptionHandler {
    @ExceptionHandler(UnmappedEndpointException.class)
    ResponseEntity<UnmappedEndpointException.Response> handleInvalidEndpointException(@NotNull UnmappedEndpointException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UnmappedEndpointException.Response(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}