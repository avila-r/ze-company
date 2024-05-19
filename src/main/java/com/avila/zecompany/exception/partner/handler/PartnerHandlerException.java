package com.avila.zecompany.exception.partner.handler;
import com.avila.zecompany.exception.partner.PartnerNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PartnerHandlerException {
    @ExceptionHandler(PartnerNotFoundException.class)
    ResponseEntity<PartnerNotFoundException.Response> handlePartnerNotFoundException(@NotNull PartnerNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new PartnerNotFoundException.Response(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}