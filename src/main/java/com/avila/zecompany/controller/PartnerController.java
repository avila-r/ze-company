package com.avila.zecompany.controller;
import com.avila.zecompany.dto.GeoDataDTO;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.dto.PartnerResponseDTO;
import com.avila.zecompany.model.GeoData;
import com.avila.zecompany.model.Partner;
import com.avila.zecompany.repository.GeoDataRepository;
import com.avila.zecompany.repository.PartnerRepository;
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
    private final PartnerRepository partnerRepository;
    private final GeoDataRepository geoDataRepository;

    @GetMapping
    public ResponseEntity<List<PartnerResponseDTO>> listAll(){
        return ResponseEntity.ok(partnerRepository.findAll().stream()
                        .map(partner -> PartnerResponseDTO.builder()
                                .id(partner.getId())
                                .tradingName(partner.getTradingName())
                                .ownerName(partner.getOwnerName())
                                .document(partner.getDocument())
                                .coverageArea(GeoDataDTO.builder()
                                        .type(geoDataRepository.findByPartnerId(partner.getId()).orElseThrow().getType().equals(GeoData.GeoType.ADDRESS) ? "Point" : "MultiPolygon")
                                        .coordinates(geoDataRepository.findByPartnerId(partner.getId()).orElseThrow().getCoordinates())
                                        .build())
                                .build())
                        .toList()
                );
    }

    @PostMapping
    @Transactional public ResponseEntity<PartnerResponseDTO> insert(@RequestBody PartnerRequestDTO request){
        Partner partner = Partner.builder()
                .document(request.document())
                .tradingName(request.tradingName())
                .ownerName(request.ownerName())
                .build();
        GeoData area = GeoData.builder()
                .partner(partner)
                .type(request.coverageArea().type().equals("Point") ? GeoData.GeoType.ADDRESS : GeoData.GeoType.AREA)
                .coordinates(request.coverageArea().coordinates())
                .build();
        GeoData address = GeoData.builder()
                .partner(partner)
                .type(request.address().type().equals("Point") ? GeoData.GeoType.ADDRESS : GeoData.GeoType.AREA)
                .coordinates(request.address().coordinates())
                .build();
        partnerRepository.save(partner);
        geoDataRepository.save(area);
        geoDataRepository.save(address);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        PartnerResponseDTO.builder()
                                .id(partnerRepository.findByDocument(partner.getDocument()).orElseThrow().getId())
                                .tradingName(partner.getTradingName())
                                .ownerName(partner.getOwnerName())
                                .document(partner.getDocument())
                                .coverageArea(GeoDataDTO.builder()
                                        .type(area.getType().equals(GeoData.GeoType.ADDRESS) ? "Point" : "MultiPolygon")
                                        .coordinates(area.getCoordinates())
                                        .build())
                                .address(GeoDataDTO.builder()
                                        .type(address.getType().equals(GeoData.GeoType.ADDRESS) ? "Point" : "MultiPolygon")
                                        .coordinates(area.getCoordinates())
                                        .build())
                                .build()
                );
    }
}