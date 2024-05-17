package com.avila.zecompany.dto;
import lombok.Builder;

@Builder
public record PartnerResponseDTO (
        Long id,
        String tradingName,
        String ownerName,
        String document,
        GeoDataDTO coverageArea,
        GeoDataDTO address) { }