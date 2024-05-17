package com.avila.zecompany.dto;
import com.avila.zecompany.model.GeoData;
import lombok.Builder;
import java.util.List;

@Builder
public record PartnerDTO (
        String tradingName,
        String ownerName,
        Long document,
        GeographyDTO coverageArea,
        GeographyDTO address
) {
    @Builder public record GeographyDTO(GeoData.GeoType type, List<?> coordinates){}
}