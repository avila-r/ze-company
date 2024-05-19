package com.avila.zecompany.controller;
import com.avila.zecompany.exception.advice.UnmappedEndpointException;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AdviceController {
    @RequestMapping("**")
    void handleUnmappedEndpoint(){
        throw new UnmappedEndpointException();
    }
}