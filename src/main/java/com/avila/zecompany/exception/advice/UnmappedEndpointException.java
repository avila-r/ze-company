package com.avila.zecompany.exception.advice;
import org.springframework.http.HttpStatus;

public class UnmappedEndpointException extends RuntimeException {
    public record Response(HttpStatus status, String message){}

    public UnmappedEndpointException(){
        super("Unmapped endpoint. See documentation at https://github.com/avila-r/ze-company to get information.");
    }
}