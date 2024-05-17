package com.avila.zecompany.controller;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.dto.PartnerResponseDTO;
import com.avila.zecompany.service.PartnerService;
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
    public ResponseEntity<PartnerResponseDTO> insert(@RequestBody PartnerRequestDTO request){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.insertNewPartner(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartnerResponseDTO> get(@PathVariable Long id){
        return ResponseEntity.ok(service.getPartner(id));
    }
}