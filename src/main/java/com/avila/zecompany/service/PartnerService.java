package com.avila.zecompany.service;
import com.avila.zecompany.dto.GeoDataDTO;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.dto.PartnerResponseDTO;
import com.avila.zecompany.model.GeoData;
import com.avila.zecompany.model.Partner;
import com.avila.zecompany.repository.PartnerRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @AllArgsConstructor
public class PartnerService {
    private final PartnerRepository repository;
    private final GeoDataService geoDataService;

    private PartnerResponseDTO build(@NotNull Partner partner){
        return PartnerResponseDTO.builder()
                .id(partner.getId())
                .tradingName(partner.getTradingName())
                .ownerName(partner.getOwnerName())
                .document(partner.getDocument())
                .coverageArea(GeoDataDTO.builder()
                        .type(geoDataService.getAreaByPartner(partner).getType().getType())
                        .coordinates(geoDataService.getAreaByPartner(partner).getCoordinates())
                        .build())
                .address(GeoDataDTO.builder()
                        .type(geoDataService.getAddressByPartner(partner).getType().getType())
                        .coordinates(geoDataService.getAddressByPartner(partner).getCoordinates())
                        .build())
                .build();
    }

    private Partner build(@NotNull PartnerRequestDTO request){
        return Partner.builder()
                .document(request.document())
                .tradingName(request.tradingName())
                .ownerName(request.ownerName())
                .build();
    }

    private PartnerResponseDTO build(@NotNull Partner partner, GeoData area, GeoData address){
        return PartnerResponseDTO.builder()
                .id(repository.findByDocument(partner.getDocument()).orElseThrow().getId()) // TODO: Custom exception
                .tradingName(partner.getTradingName())
                .ownerName(partner.getOwnerName())
                .document(partner.getDocument())
                .coverageArea(build(area))
                .address(build(address))
                .build();
    }

    private GeoDataDTO build(@NotNull GeoData geoData){
        return GeoDataDTO.builder()
                .type(geoData.getType().getType())
                .coordinates(geoData.getCoordinates())
                .build();
    }

    public List<PartnerResponseDTO> listAllPartners(){
        return repository.findAll().stream()
                .map(this::build)
                .toList();
    }

    public PartnerResponseDTO getPartner(Long id){
        return build(repository.findById(id).orElseThrow()); // TODO: Custom exception
    }

    @Transactional public PartnerResponseDTO insertNewPartner(PartnerRequestDTO request){
        return build(
                repository.save(build(request)),
                geoDataService.saveAreaByPartnerRequest(request, repository.findByDocument(build(request).getDocument()).orElseThrow()), // TODO: Custom exception
                geoDataService.saveAddressPartnerRequest(request, repository.findByDocument(build(request).getDocument()).orElseThrow()) // TODO: Custom exception
        );
    }
}