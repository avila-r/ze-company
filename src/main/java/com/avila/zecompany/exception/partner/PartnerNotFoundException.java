package com.avila.zecompany.exception.partner;
import org.springframework.http.HttpStatus;

public class PartnerNotFoundException extends RuntimeException {
    public record Response(HttpStatus status, String message){}

    public PartnerNotFoundException(){
        super("Partner not found");
    }
}