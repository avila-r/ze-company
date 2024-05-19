package com.avila.zecompany.exception.geodata.handler;
import com.avila.zecompany.exception.geodata.GeoDataNotFoundException;
import com.avila.zecompany.exception.geodata.InvalidGeoDataTypeException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GeoDataExceptionHandler {
    @ExceptionHandler(InvalidGeoDataTypeException.class)
    ResponseEntity<InvalidGeoDataTypeException.Response> handleInvalidGeoDataTypeException(@NotNull InvalidGeoDataTypeException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new InvalidGeoDataTypeException.Response(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
    @ExceptionHandler(GeoDataNotFoundException.class)
    ResponseEntity<GeoDataNotFoundException.Response> handleGeoDataNotFoundException(@NotNull GeoDataNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new GeoDataNotFoundException.Response(HttpStatus.NOT_FOUND, e.getMessage()));
    }
}