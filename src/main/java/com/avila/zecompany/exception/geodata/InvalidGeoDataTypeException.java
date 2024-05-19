package com.avila.zecompany.exception.geodata;
import org.springframework.http.HttpStatus;

public class InvalidGeoDataTypeException extends RuntimeException {
    public record Response(HttpStatus status, String message){}

    public InvalidGeoDataTypeException(){
        super("Invalid GeoData.GeoType. Addresses should be 'Point' and coverage areas should be 'MultiPolygon'.");
    }
}