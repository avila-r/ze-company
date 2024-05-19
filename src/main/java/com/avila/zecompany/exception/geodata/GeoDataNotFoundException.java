package com.avila.zecompany.exception.geodata;
import com.avila.zecompany.model.GeoData;
import com.avila.zecompany.model.Partner;
import org.springframework.http.HttpStatus;

public class GeoDataNotFoundException extends RuntimeException {
    public record Response(HttpStatus status, String message){}

    public GeoDataNotFoundException(Partner partner, GeoData.GeoType type){
        super("GeoData of type " + type + " and partner " + partner + " was not found");
    }
}