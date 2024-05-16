package com.avila.zecompany.dto;
import com.avila.zecompany.model.Geography;
import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter
@Builder
public record PartnerDTO (
        Long id,
        String tradingName,
        String ownerName,
        Long document,
        GeographyDTO coverageArea,
        GeographyDTO address
) {
    @Builder public record GeographyDTO(Geography.GeoType type, List<?> coordinates){}
}