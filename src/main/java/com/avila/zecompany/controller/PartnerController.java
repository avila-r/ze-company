package com.avila.zecompany.controller;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.dto.PartnerResponseDTO;
import com.avila.zecompany.service.PartnerService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/partner")
@AllArgsConstructor
public class PartnerController {
    private final PartnerService service;

    @GetMapping
    public ResponseEntity<List<PartnerResponseDTO>> listAll(){
        return ResponseEntity.ok(service.listAllPartners());
    }

    @PostMapping
    @Transactional public ResponseEntity<PartnerResponseDTO> insert(@RequestBody PartnerRequestDTO request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.insertNewPartner(request));
    }
}