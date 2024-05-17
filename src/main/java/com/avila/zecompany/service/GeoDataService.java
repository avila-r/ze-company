package com.avila.zecompany.service;
import com.avila.zecompany.dto.PartnerRequestDTO;
import com.avila.zecompany.model.GeoData;
import com.avila.zecompany.model.Partner;
import com.avila.zecompany.repository.GeoDataRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @AllArgsConstructor
public class GeoDataService {
    private final GeoDataRepository repository;

    public GeoData getAreaByPartner(@NotNull Partner partner){
        return repository.findByPartnerIdAndType(partner.getId(), GeoData.GeoType.AREA)
                .orElseThrow(); // TODO: Custom exception
    }

    public GeoData getAddressByPartner(@NotNull Partner partner){
        return repository.findByPartnerIdAndType(partner.getId(), GeoData.GeoType.ADDRESS)
                .orElseThrow(); // TODO: Custom exception
    }

    @Transactional public GeoData saveAreaByPartnerRequest(@NotNull PartnerRequestDTO request, @NotNull Partner partner){
        if (request.address().type().equals("Point")) return repository.save(
                GeoData.builder()
                        .partner(partner)
                        .type(GeoData.GeoType.ADDRESS)
                        .coordinates(request.address().coordinates())
                        .build());
        else throw new RuntimeException(); // TODO: Custom exception
    }

    @Transactional public GeoData saveAddressPartnerRequest(@NotNull PartnerRequestDTO request, @NotNull Partner partner){
        if (request.coverageArea().type().equals("MultiPolygon")) return repository.save(
                GeoData.builder()
                        .partner(partner)
                        .type(GeoData.GeoType.AREA)
                        .coordinates(request.coverageArea().coordinates())
                        .build());
        else throw new RuntimeException(); // TODO: Custom exception
    }
}